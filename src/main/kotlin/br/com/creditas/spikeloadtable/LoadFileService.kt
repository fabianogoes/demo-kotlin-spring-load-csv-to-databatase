package br.com.creditas.spikeloadtable

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class LoadFileService(private val mapper: ObjectMapper) {

    fun loadJsonFromResourcesFile() {
        val resource: Resource = ClassPathResource(JSON_FILE_PATH) as Resource
        val stream = resource.inputStream
        val person: Person = mapper.readValue(stream, Person::class.java)
        println("... Load Json Configuration...")
        println(person)
    }

    fun loadListOfFiles(totalTablesToRun: Int): ResponseDTO {
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
        return ResponseDTO(
                totalLinesInMemory = TOTAL_LINES,
                currentTableLines = currentTableLines,
                currentProcessTime = processTimer,
                totalTablesInMemory = TOTAL_TABLES
        )
    }

    private fun showProcessTimer(startProcess: LocalDateTime?, completeList: MutableList<Record>): String {
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

    fun loadCsvFromResourcesFile(): List<Record> {
        val resource: Resource = ClassPathResource(CSV_FILE_PATH) as Resource
        val reader = resource.inputStream.bufferedReader()
        val csvFormat = CSVFormat.DEFAULT.withDelimiter(';')
        val csvParser = CSVParser(reader, csvFormat)

        return csvParser.map {
            Record(
                    recordNumber = it.recordNumber,
                    rangeLTV = it.get(0),
                    rangeTicket = it.get(1),
                    rangeScore = it.get(2),
                    maturity = it.get(3),
                    carAge = it.get(4),
                    rangeRendaBruta = it.get(5),
                    regiao = it.get(6),
                    taxa = it.get(7)
            )
        }
    }

    companion object {
        const val JSON_FILE_PATH = "json/example.json"
        const val CSV_FILE_PATH = "csv/table.csv"
        var COMPLETE_LIST = mutableListOf<Record>()
        var TOTAL_LINES = 0
        var TOTAL_TABLES = 0
    }

}

data class Person(val id: String? = "", val name: String? = "")

data class Record(
        val recordNumber: Long,
        val rangeLTV: String,
        val rangeTicket: String,
        val rangeScore: String,
        val maturity: String,
        val carAge: String,
        val rangeRendaBruta: String,
        val regiao: String,
        val taxa: String
)

data class ResponseDTO(
        val currentProcessTime: String,
        val currentTableLines: Int,
        val totalLinesInMemory: Int,
        val totalTablesInMemory: Int)