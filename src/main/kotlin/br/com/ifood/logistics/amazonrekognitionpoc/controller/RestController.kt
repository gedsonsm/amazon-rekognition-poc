package br.com.ifood.logistics.amazonrekognitionpoc.controller

import br.com.ifood.logistics.amazonrekognitionpoc.rekognition.RekognitionService
import br.com.ifood.logistics.amazonrekognitionpoc.vo.CompareFacesResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class RestController(private val rekognitionService: RekognitionService) {

    @PostMapping(value = ["/compareFaces"])
    fun compareFaces(
        @RequestParam imageSource: MultipartFile,
        @RequestParam imageTarget: MultipartFile
    ): List<CompareFacesResponse> {
        return rekognitionService.compareFaces(imageSource, imageTarget)
    }
}