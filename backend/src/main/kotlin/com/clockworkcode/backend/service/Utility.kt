package com.clockworkcode.backend.service

class Utility {

    fun extractAirportName(airportName: String): String {
        return airportName.replace(Regex("-\\d+$"), "")
    }

}