package br.com.creditas.spikeloadtable

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoadCsvController(val loadFileService: LoadFileService) {

    @RequestMapping("/load/{count}")
    fun loadCsvList(@PathVariable count: Int): ResponseDTO {
        return loadFileService.loadListOfFiles(count)
    }

}