package br.com.creditas.spikeloadtable.service

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.model.ResponseTaxaDTO
import br.com.creditas.spikeloadtable.repository.ConfigurationRepository
import br.com.creditas.spikeloadtable.repository.ConfigurationRepositoryJpa
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
        private val repositoryCsv: ConfigurationRepository,
        private val repositoryJpa: ConfigurationRepositoryJpa
) {

    fun findTaxaCsv(configuration: Configuration) =
            repositoryCsv.findTaxaByConfiguration(configuration)

    fun findTaxaJpa(configuration: Configuration) =
            repositoryJpa.findTaxa(
                    rangeLTV = configuration.rangeLTV,
                    rangeTicket = configuration.rangeTicket,
                    rangeScore = configuration.rangeScore,
                    maturity = configuration.maturity,
                    carAge = configuration.carAge,
                    rangeRendaBruta = configuration.rangeRendaBruta,
                    regiao = configuration.regiao)
                    .map { ResponseTaxaDTO(it.taxa!!) }
                    .first()


}
