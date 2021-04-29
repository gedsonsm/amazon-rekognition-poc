package br.com.ifood.logistics.amazonrekognitionpoc.rekognition

import com.amazonaws.ClientConfiguration
import com.amazonaws.Protocol
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.rekognition.AmazonRekognition
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AmazonRekognitionFactory(
    @Value("\${amazon.credentials.accessKey}") private val awsAccessKey: String,
    @Value("\${amazon.credentials.secretKey}") private val awsSecretKey: String,
    @Value("\${amazon.credentials.region}") private val awsRegion: String
) {

    private var amazonRekognition: AmazonRekognition? = null

    fun create(): AmazonRekognition {
        return when {
            amazonRekognition != null -> amazonRekognition!!
            else -> {
                val clientConfig = ClientConfiguration()
                clientConfig.connectionTimeout = 30000
                clientConfig.requestTimeout = 60000
                clientConfig.protocol = Protocol.HTTPS

                val staticCredentials = AWSStaticCredentialsProvider(
                    BasicAWSCredentials(awsAccessKey, awsSecretKey)
                )

                AmazonRekognitionClientBuilder
                    .standard()
                    .withClientConfiguration(clientConfig)
                    .withCredentials(staticCredentials)
                    .withRegion(awsRegion)
                    .build()
            }
        }
    }
}