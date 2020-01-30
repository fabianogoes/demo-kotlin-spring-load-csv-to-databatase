package br.com.creditas.spikeloadtable.repository

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.model.ResponseTaxaDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

interface ConfigurationRepository {
    fun findAll(): List<Configuration>
    fun findTaxaByConfiguration(configurationParam: Configuration): ResponseTaxaDTO
}

@Repository
class ConfigurationRepositoryImpl : ConfigurationRepository {

    @Value("\${files.path.csv}")
    private val csvFilePath = "csv/table.csv"

    init {
        Database.factory(csvFilePath = csvFilePath)
    }

    override fun findAll() = Database.getCompleteTable()

    override fun findTaxaByConfiguration(configuration: Configuration) = findAll()
            .filter { it == configuration }
            .map{ ResponseTaxaDTO(it.taxa!!) }
            .first()

}