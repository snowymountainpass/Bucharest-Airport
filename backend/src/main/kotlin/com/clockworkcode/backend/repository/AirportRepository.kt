package com.clockworkcode.backend.repository

import com.clockworkcode.backend.model.Airport
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AirportRepository : JpaRepository<Airport, UUID> {

    fun findAirportByAirportName(airportName: String):Airport
    fun findAirportByAirportNameContains(airportName: String):Airport
}