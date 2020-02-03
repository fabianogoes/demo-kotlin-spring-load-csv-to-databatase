package br.com.creditas.spikeloadtable.repository

import br.com.creditas.spikeloadtable.model.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ConfigurationRepositoryJpa : JpaRepository<Configuration, Long> {

    @Query(value =
            "from Configuration c " +
            "WHERE c.rangeLTV = :rangeLTV" +
            "  AND c.rangeTicket = :rangeTicket " +
            "  AND c.rangeScore = :rangeScore " +
            "  AND c.maturity = :maturity " +
            "  AND c.carAge = :carAge " +
            "  AND c.rangeRendaBruta = :rangeRendaBruta " +
            "  AND c.regiao = :regiao")
    fun findTaxa(rangeLTV: String,
                 rangeTicket: String,
                 rangeScore: String,
                 maturity: Int,
                 carAge: String,
                 rangeRendaBruta: String,
                 regiao: String): List<Configuration>

}

