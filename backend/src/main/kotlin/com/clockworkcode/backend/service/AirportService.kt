package com.clockworkcode.backend.service

import com.clockworkcode.backend.model.Airport
import com.clockworkcode.backend.repository.AirportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AirportService @Autowired constructor(private val airportRepository: AirportRepository) {

    fun increaseOccupiedParkingSpaces(airport : Airport) {
        airport.numberOfOccupiedParkingSpaces++
        airportRepository.save(airport)
    }
    fun decreaseOccupiedParkingSpaces(airport : Airport) {
        airport.numberOfOccupiedParkingSpaces--
        airportRepository.save(airport)
    }

    fun getAirportByAirportName(airportName:String):Airport{
        return airportRepository.findAirportByAirportName(airportName)
    }

}