package br.com.creditas.spikeloadtable.repository

import br.com.creditas.spikeloadtable.model.Configuration
import br.com.creditas.spikeloadtable.model.ResponseProcessLoadDTO
import br.com.creditas.spikeloadtable.util.ProcessTimer
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import java.time.LocalDateTime

object Database {

    private val logger: Logger = LoggerFactory.getLogger(Database::class.java)

    private var completeTable = mutableListOf<Configuration>()
    private var totalLines = 0
    private var totalTables = 0

    fun factory(totalTablesToRun: Int? = 1, csvFilePath: String): ResponseProcessLoadDTO {
        logger.info("<<< Loading Table... >>>")
        val startProcess = LocalDateTime.now()
        totalTables += totalTablesToRun!!

        var currentTableLines = 0
        for (x in 1..totalTablesToRun) {
            val list = loadCsvFromResourcesFile(csvFilePath)
            currentTableLines += list.count()
            completeTable.addAll(list)
        }
        totalLines += currentTableLines

        val processTimer = ProcessTimer.print(startProcess, completeTable)
        return ResponseProcessLoadDTO(
                totalLinesInMemory = totalLines,
                currentTableLines = currentTableLines,
                currentProcessTime = processTimer,
                totalTablesInMemory = totalTables
        )
    }

    fun getCompleteTable() = completeTable

    private fun loadCsvFromResourcesFile(csvFilePath: String): List<Configuration> {
        val resource: Resource = ClassPathResource(csvFilePath) as Resource
        val reader = resource.inputStream.bufferedReader()
        val csvFormat = CSVFormat.DEFAULT.withDelimiter(';')
        val csvParser = CSVParser(reader, csvFormat)

        return csvParser.map(Database::mapToRecord)
    }

    private fun mapToRecord(csvLine: CSVRecord): Configuration {
        return Configuration(
                id = csvLine.recordNumber,
                rangeLTV = csvLine.get(0),
                rangeTicket = csvLine.get(1),
                rangeScore = csvLine.get(2),
                maturity = csvLine.get(3).toInt(),
                carAge = csvLine.get(4),
                rangeRendaBruta = csvLine.get(5),
                regiao = csvLine.get(6),
                taxa = csvLine.get(7)
        )
    }

}