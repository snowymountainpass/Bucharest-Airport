package com.clockworkcode.backend.repository

import com.clockworkcode.backend.model.ParkingSpace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ParkingSpaceRepository : JpaRepository<ParkingSpace, UUID>{

    fun findParkingSpacesByOccupied(occupied:Boolean): List<ParkingSpace>
    fun findParkingSpacesByAirportName(airportName:String): List<ParkingSpace>
    fun findParkingSpacesByAirportCode(airportCode:String): List<ParkingSpace>
}