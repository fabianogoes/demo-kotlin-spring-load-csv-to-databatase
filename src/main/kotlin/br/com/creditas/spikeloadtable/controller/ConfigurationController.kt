package br.com.creditas.spikeloadtable.controller

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.service.ConfigurationService
import org.springframework.web.bind.annotation.*

@RestController
class ConfigurationController(val configurationService: ConfigurationService) {

    @GetMapping("/configuration")
    fun getTaxByConfiguration(
            @RequestParam(value = "rangeLTV") rangeLTV: String,
            @RequestParam(value = "rangeTicket") rangeTicket: String,
            @RequestParam(value = "rangeScore") rangeScore: String,
            @RequestParam(value = "maturity") maturity: String,
            @RequestParam(value = "carAge") carAge: String,
            @RequestParam(value = "rangeRendaBruta") rangeRendaBruta: String,
            @RequestParam(value = "regiao") regiao: String,
            configuration: Configuration
    ) = configurationService.findTaxaByConfiguration(configuration)
}