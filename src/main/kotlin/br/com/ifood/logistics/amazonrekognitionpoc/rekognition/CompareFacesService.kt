package br.com.ifood.logistics.amazonrekognitionpoc.rekognition

import br.com.ifood.logistics.amazonrekognitionpoc.vo.CompareFacesResponse
import com.amazonaws.services.rekognition.model.CompareFacesRequest
import com.amazonaws.services.rekognition.model.CompareFacesResult
import com.amazonaws.services.rekognition.model.Image
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.ByteBuffer


@Service
class RekognitionService {

    fun compareFaces(imageSource: MultipartFile, imageTarget: MultipartFile): List<CompareFacesResponse> {
        val request = CompareFacesRequest()
            .withSourceImage(Image().withBytes(ByteBuffer.wrap(imageSource.bytes)))
            .withTargetImage(Image().withBytes(ByteBuffer.wrap(imageTarget.bytes)))
            .withSimilarityThreshold(SIMILARITY_THRESHOLD)

        val result: CompareFacesResult = amazonRekognition.compareFaces(request);

        var response: MutableList<CompareFacesResponse> =
            result.faceMatches.map {
                CompareFacesResponse(
                    match = true,
                    similarity = it.similarity,
                    confidence = it.face.confidence
                )
            }.toMutableList()

        response.addAll(result.unmatchedFaces.map {
            CompareFacesResponse(
                confidence = it.confidence
            )
        })

        return response
    }

    companion object {
        private const val SIMILARITY_THRESHOLD = 70f
        private val amazonRekognition = AmazonRekognitionFactory.create()
    }
}