package br.com.creditas.spikeloadtable.service

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.repository.ConfigurationRepository
import org.springframework.stereotype.Service

@Service
class ConfigurationService(private val repository: ConfigurationRepository) {

    fun findTaxaByConfiguration(configuration: Configuration) =
            repository.findTaxaByConfiguration(configuration)

}
