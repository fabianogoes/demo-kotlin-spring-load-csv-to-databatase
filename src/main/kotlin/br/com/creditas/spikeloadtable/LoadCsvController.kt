package br.com.creditas.spikeloadtable

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class LoadCsvController(val configurationTableService: ConfigurationTableService) {

    @GetMapping("/load/{count}")
    fun loadCsvList(@PathVariable count: Int): ResponseProcessLoadDTO {
        return configurationTableService.loadListOfCsvFiles(count)
    }

    @GetMapping("/configuration/{rangeLTV:.+}/{rangeTicket:.+}/{rangeScore:.+}/{maturity:.+}/{carAge:.+}/{rangeRendaBruta:.+}/{regiao}")
    fun findConfigurationFromTableByCompleteKey(
            @PathVariable rangeLTV: String,
            @PathVariable rangeTicket: String,
            @PathVariable rangeScore: String,
            @PathVariable maturity: String,
            @PathVariable carAge: String,
            @PathVariable rangeRendaBruta: String,
            @PathVariable regiao: String
    ): ResponseTaxaDTO {
        return configurationTableService
                .findCompleteKey(rangeLTV, rangeTicket, rangeScore, maturity, carAge, rangeRendaBruta, regiao)
    }

}