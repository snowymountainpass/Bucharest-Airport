package com.clockworkcode.backend.controller

import com.clockworkcode.backend.service.AirportService
import com.clockworkcode.backend.service.TransactionService
import com.clockworkcode.backend.service.Utility
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/transaction")
@CrossOrigin
class TransactionController @Autowired constructor(
    private val transactionService: TransactionService,
    private val airportService: AirportService
    ) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/addTransaction")
    fun addTransaction(@RequestBody response: Map<String, String>): ResponseEntity<Map<String, Boolean>>{
        var resultMap = HashMap<String, String>()
        var responseMap = HashMap<String, Boolean>()
        //TODO: add validation for license plate
        //TODO: add validation for airport

        if(response.containsKey("licensePlate") && response.containsKey("airport") ){
            resultMap["licensePlate"] = response["licensePlate"].toString()
            val utility = Utility()
            resultMap["airport"] =  utility.extractAirportName(response["airport"].toString())
        }

        var isValidCarLicensePlate=true
        if (isValidCarLicensePlate){
            val airport = airportService.getAirportByPartialAirportName(resultMap["airport"]!!)
            transactionService.addTransaction(
                resultMap["licensePlate"]!!,
                airport)
                airportService.increaseOccupiedParkingSpaces(airport)
                logger.info { "A new transaction was added for license plate "+ resultMap["licensePlate"]!! + " @ " + LocalDateTime.now() }
                responseMap.put("result",true)
        }else{
            logger.info { "Invalid car license plate " + resultMap["licensePlate"]!! + " @ " + LocalDateTime.now() }
            responseMap.put("result",false)
        }

        return ResponseEntity.ok(responseMap)
    }

}