package br.com.creditas.spikeloadtable.model

import javax.persistence.*

@Entity
@Table(name = "TBL_CONFIGURATION")
data class Configuration(
        @Id
        @Column(name = "ID")
        var id: Long? = 0,
        @Column(name = "RANGE_LTV")
        val rangeLTV: String,
        @Column(name = "RANGE_TICKET")
        val rangeTicket: String,
        @Column(name = "RANGE_SCORE")
        val rangeScore: String,
        @Column(name = "MATURITY")
        val maturity: Int,
        @Column(name = "CAR_AGE")
        val carAge: String,
        @Column(name = "RANGE_RENDA_BRUTA")
        val rangeRendaBruta: String,
        @Column(name = "REGIAO")
        val regiao: String,
        @Column(name = "TAXA")
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
