package br.com.ifood.logistics.amazonrekognitionpoc.rekognition

import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder

import com.amazonaws.auth.profile.ProfileCredentialsProvider

import com.amazonaws.auth.AWSCredentialsProvider

import com.amazonaws.ClientConfiguration
import com.amazonaws.Protocol
import com.amazonaws.regions.Regions

import com.amazonaws.services.rekognition.AmazonRekognition

class AmazonRekognitionFactory {

    companion object {
        private const val PROFILE_NAME = "default"

        fun create(): AmazonRekognition {
            val clientConfig = ClientConfiguration()
            clientConfig.connectionTimeout = 30000
            clientConfig.requestTimeout = 60000
            clientConfig.protocol = Protocol.HTTPS

            val credentialsProvider: AWSCredentialsProvider = ProfileCredentialsProvider(PROFILE_NAME)

            return AmazonRekognitionClientBuilder
                .standard()
                .withClientConfiguration(clientConfig)
                .withCredentials(credentialsProvider)
                .build()
        }
    }
}