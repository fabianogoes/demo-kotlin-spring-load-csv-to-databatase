package br.com.creditas.spikeloadtable.model

data class ResponseProcessLoadDTO(
        val currentProcessTime: String,
        val currentTableLines: Int,
        val totalLinesInMemory: Int,
        val totalTablesInMemory: Int)

data class ResponseTaxaDTO(val taxa: String)