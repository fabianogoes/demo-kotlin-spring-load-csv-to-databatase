package br.com.creditas.spikeloadtable.controller

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.service.ConfigurationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/configuration")
class ConfigurationController(val configurationService: ConfigurationService) {

    @GetMapping("/csv")
    fun getTaxByConfigurationCsv(
            @RequestParam(value = "rangeLTV") rangeLTV: String,
            @RequestParam(value = "rangeTicket") rangeTicket: String,
            @RequestParam(value = "rangeScore") rangeScore: String,
            @RequestParam(value = "maturity") maturity: String,
            @RequestParam(value = "carAge") carAge: String,
            @RequestParam(value = "rangeRendaBruta") rangeRendaBruta: String,
            @RequestParam(value = "regiao") regiao: String,
            configuration: Configuration
    ) = configurationService.findTaxaCsv(configuration)

    @GetMapping("/jpa")
    fun getTaxByConfigurationJpa(
            rangeLTV: String,
            rangeTicket: String,
            rangeScore: String,
            maturity: Int,
            carAge: String,
            rangeRendaBruta: String,
            regiao: String,
            configuration: Configuration
    ) = configurationService.findTaxaJpa(configuration)
}