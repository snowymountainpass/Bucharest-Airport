package com.clockworkcode.backend.service

class Utility {

    //Extracts the name of the airport by removing the - and the digits from the end of the String
    //Example: from "text-123", we replace "-123" with ""; the airport name is returned
    fun extractAirportName(airportName: String): String {
        return airportName.replace(Regex("-\\d+$"), "")
    }

    // Removes any hyphens (-) or ' ' from the String
    fun cleanLicensePlate(licensePlate: String): String {
        return licensePlate.replace(Regex("[- ]"), "")
    }

    //A license plate is valid if it contains only letters (uppercase and lowercase) and digits
    fun isValidLicensePlate(licensePlate: String):Boolean {
        return licensePlate.matches(Regex("^[A-Za-z0-9]+$"))
    }

    fun isValidAgainstSQLInjection(licensePlate: String):Boolean{
        return !licensePlate
            .matches(Regex("(?i)(select|insert|update|delete|drop|create|grant|revoke|truncate|union|exec|execute|declare|cast|convert|alter|table|where|join|order\\\\s+by|group\\\\s+by|having|limit|offset|fetch|into|exists|distinct|or\\\\s+1=1|--|;|')"))
    }

}