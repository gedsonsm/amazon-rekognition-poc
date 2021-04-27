package br.com.ifood.logistics.amazonrekognitionpoc.rekognition

import br.com.ifood.logistics.amazonrekognitionpoc.vo.CompareFacesResponse
import com.amazonaws.services.rekognition.model.CompareFacesRequest
import com.amazonaws.services.rekognition.model.CompareFacesResult
import com.amazonaws.services.rekognition.model.Image
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.ByteBuffer


@Service
class RekognitionService(
    private val awsRekognitionFactory: AmazonRekognitionFactory
) {

    fun compareFaces(imageSource: MultipartFile, imageTarget: MultipartFile): List<CompareFacesResponse> {
        val request = CompareFacesRequest()
            .withSourceImage(Image().withBytes(ByteBuffer.wrap(imageSource.bytes)))
            .withTargetImage(Image().withBytes(ByteBuffer.wrap(imageTarget.bytes)))
            .withSimilarityThreshold(SIMILARITY_THRESHOLD)

        val amazonRekognition = awsRekognitionFactory.create()

        val result = amazonRekognition.compareFaces(request)

        return executeFaceMatchRules(result)
    }

    private fun executeFaceMatchRules(result: CompareFacesResult, considerOnlyOneFace: Boolean = false) = when {
        result.faceMatches.isEmpty() && result.unmatchedFaces.isNotEmpty() -> {
            result.unmatchedFaces.map {
                CompareFacesResponse(confidence = it.confidence)
            }
        }

        /**
         * Just in case we want to consider "more than one person in target picture" as NOT MATCH.
         * */
        considerOnlyOneFace && result.unmatchedFaces.isNotEmpty() -> {
            result.unmatchedFaces.map {
                CompareFacesResponse(confidence = it.confidence)
            }
        }

        result.faceMatches.isNotEmpty() -> {
            result.faceMatches.map {
                CompareFacesResponse(
                    match = true,
                    similarity = it.similarity,
                    confidence = it.face.confidence
                )
            }
        }

        else -> throw RuntimeException("No face detected")
    }

    companion object {
        private const val SIMILARITY_THRESHOLD = 70f
    }
}