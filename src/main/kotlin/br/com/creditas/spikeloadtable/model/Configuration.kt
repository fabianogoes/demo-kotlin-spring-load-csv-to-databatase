package br.com.creditas.spikeloadtable.model

data class Configuration(
        var id: Long? = 0,
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
