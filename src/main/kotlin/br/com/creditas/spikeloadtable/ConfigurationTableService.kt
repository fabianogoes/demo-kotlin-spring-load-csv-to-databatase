package br.com.creditas.spikeloadtable

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class ConfigurationTableService(private val mapper: ObjectMapper) {

    @Value("\${files.path.json}")
    val jsonFilePath = "json/example.json"

    @Value("\${files.path.csv}")
    val csvFilePath = "csv/table.csv"

    fun loadJsonFromResourcesFile() {
        val resource: Resource = ClassPathResource(jsonFilePath) as Resource
        val stream = resource.inputStream
        val person: Person = mapper.readValue(stream, Person::class.java)
        println("... Load Json Configuration...")
        println(person)
    }

    fun loadListOfCsvFiles(totalTablesToRun: Int): ResponseProcessLoadDTO {
        val startProcess = LocalDateTime.now()
        TOTAL_TABLES += totalTablesToRun

        var currentTableLines = 0
        for (x in 1..totalTablesToRun) {
            val list = loadCsvFromResourcesFile()
            currentTableLines += list.count()
            COMPLETE_LIST.addAll(list)
        }
        TOTAL_LINES += currentTableLines

        val processTimer = showProcessTimer(startProcess, COMPLETE_LIST)
        return ResponseProcessLoadDTO(
                totalLinesInMemory = TOTAL_LINES,
                currentTableLines = currentTableLines,
                currentProcessTime = processTimer,
                totalTablesInMemory = TOTAL_TABLES
        )
    }

    private fun showProcessTimer(startProcess: LocalDateTime?, completeList: MutableList<Configuration>): String {
        val endProcess = LocalDateTime.now()
        val dur = Duration.between(startProcess, endProcess)
        val millis = dur.toMillis()

        completeList.count().also { println("Total Rows: $it") }
        val processTime = String.format("Duration: %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
        println(processTime)
        return processTime
    }

    private fun loadCsvFromResourcesFile(): List<Configuration> {
        val resource: Resource = ClassPathResource(csvFilePath) as Resource
        val reader = resource.inputStream.bufferedReader()
        val csvFormat = CSVFormat.DEFAULT.withDelimiter(';')
        val csvParser = CSVParser(reader, csvFormat)

        return csvParser.map(::mapToRecord)
    }

    private fun mapToRecord(csvLine: CSVRecord): Configuration {
        return Configuration(
                recordNumber = csvLine.recordNumber,
                rangeLTV = csvLine.get(0),
                rangeTicket = csvLine.get(1),
                rangeScore = csvLine.get(2),
                maturity = csvLine.get(3),
                carAge = csvLine.get(4),
                rangeRendaBruta = csvLine.get(5),
                regiao = csvLine.get(6),
                taxa = csvLine.get(7)
        )
    }

    fun findCompleteKey(rangeLTV: String, rangeTicket: String, rangeScore: String, maturity: String, carAge: String, rangeRendaBruta: String, regiao: String): ResponseTaxaDTO {
        val configurationParam = Configuration(
                rangeLTV = rangeLTV,
                rangeTicket = rangeTicket,
                rangeScore = rangeScore,
                maturity = maturity,
                carAge = carAge,
                rangeRendaBruta = rangeRendaBruta,
                regiao = regiao
        )
        return COMPLETE_LIST
                .filter { it == configurationParam }
                .map{ ResponseTaxaDTO(it.taxa!!) }
                .first()
    }

    companion object {
        var COMPLETE_LIST = mutableListOf<Configuration>()
        var TOTAL_LINES = 0
        var TOTAL_TABLES = 0
    }

}

data class Person(val id: String? = "", val name: String? = "")

data class Configuration(
        var recordNumber: Long? = 0,
        val rangeLTV: String,
        val rangeTicket: String,
        val rangeScore: String,
        val maturity: String,
        val carAge: String,
        val rangeRendaBruta: String,
        val regiao: String,
        var taxa: String? = null



) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Configuration

        if (rangeLTV != other.rangeLTV) return false
        if (rangeTicket != other.rangeTicket) return false
        if (rangeScore != other.rangeScore) return false
        if (maturity != other.maturity) return false
        if (carAge != other.carAge) return false
        if (rangeRendaBruta != other.rangeRendaBruta) return false
        if (regiao != other.regiao) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rangeLTV.hashCode()
        result = 31 * result + rangeTicket.hashCode()
        result = 31 * result + rangeScore.hashCode()
        result = 31 * result + maturity.hashCode()
        result = 31 * result + carAge.hashCode()
        result = 31 * result + rangeRendaBruta.hashCode()
        result = 31 * result + regiao.hashCode()
        return result
    }
}

data class ResponseProcessLoadDTO(
        val currentProcessTime: String,
        val currentTableLines: Int,
        val totalLinesInMemory: Int,
        val totalTablesInMemory: Int)

data class ResponseTaxaDTO(val taxa: String)