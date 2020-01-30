package br.com.creditas.spikeloadtable.repository

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.model.ResponseTaxaDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

interface ConfigurationRepository {
    fun findAll(): List<Configuration>
    fun findTaxaByConfiguration(configurationParam: Configuration): ResponseTaxaDTO
}

@Repository
class ConfigurationRepositoryImpl : ConfigurationRepository {

    private val logger: Logger = LoggerFactory.getLogger(ConfigurationRepositoryImpl::class.java)

    @Value("\${files.path.csv}")
    private val csvFilePath = "csv/table.csv"

    init {
        logger.info("<<< Building Database... >>>")
        Database.factory(csvFilePath = csvFilePath)
    }

    override fun findAll() = Database.getCompleteTable()

    override fun findTaxaByConfiguration(configuration: Configuration) = findAll()
            .filter { it == configuration }
            .map{ ResponseTaxaDTO(it.taxa!!) }
            .first()

}