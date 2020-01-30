package br.com.creditas.spikeloadtable.util

import br.com.creditas.spikeloadtable.model.Configuration
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object ProcessTimer {

    fun print(startProcess: LocalDateTime?, completeList: MutableList<Configuration>): String {
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

}