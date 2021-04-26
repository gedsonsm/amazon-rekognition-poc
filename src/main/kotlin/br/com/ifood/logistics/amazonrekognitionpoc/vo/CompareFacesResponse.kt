package br.com.ifood.logistics.amazonrekognitionpoc.vo

data class CompareFacesResponse(
    val match: Boolean = false,
    val similarity: Float? = null,
    val confidence: Float?
)
