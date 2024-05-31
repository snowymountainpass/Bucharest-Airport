package com.clockworkcode.backend.controller

import com.clockworkcode.backend.model.Airport
import com.clockworkcode.backend.model.Transaction
import com.clockworkcode.backend.service.AirportService
import com.clockworkcode.backend.service.TransactionService
import com.clockworkcode.backend.service.Utility
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

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

        var responseMap = HashMap<String, Boolean>()
        val utility = Utility()
        var licensePlate=""
        var airportName=""

        if(response.containsKey("licensePlate") && response.containsKey("airport") ){
            licensePlate = utility.cleanLicensePlate(response["licensePlate"].toString().uppercase(Locale.getDefault()))
            airportName = utility.extractAirportName(response["airport"].toString())
        }

        val isValidCarLicensePlate= utility.isValidLicensePlate(licensePlate) && utility.isValidAgainstSQLInjection(licensePlate)

        if (isValidCarLicensePlate){
            val airport:Airport = airportService.getAirportByPartialAirportName(airportName)

            //Create a new transaction only if no open/unpaid transaction already exists
            if (transactionService.getUnpaidTransaction(licensePlate) !=null) {
                //Open/unpaid transaction
                logger.info { "Unpaid transaction for license plate "+ licensePlate + " @ " + LocalDateTime.now() }
            }
            //if there is a recent transaction
            val transaction: Transaction? = transactionService.findLatestTransactionForLicensePlate(licensePlate)
            if(transaction !=null) {
                //CurrentDateTime is before Transaction DepartureTime => Car has reached barrier intending to exit the parking lot
                if(LocalDateTime.now() < transaction.departureTime!!){
                    logger.info { "Barrier RAISED for license plate "+ licensePlate + " @ " + LocalDateTime.now() }
                }
                // CurrentDateTime is after Transaction DepartureTime => Car has reached barrier intending to exit the parking lot
                // after the allotted exit period
                else if(LocalDateTime.now() > transaction.departureTime!!){
                    transactionService.addTransaction(
                        licensePlate,
                        airport)
                    airportService.increaseOccupiedParkingSpaces(airport)
                    logger.info { "A new transaction was added for license plate "+ licensePlate + " @ " + LocalDateTime.now() }
                    logger.info { "Barrier RAISED for license plate "+ licensePlate + " @ " + LocalDateTime.now() }
                    responseMap.put("result",true)
                }
            }else{
                // First time entering using the parking lot
                transactionService.addTransaction(
                    licensePlate,
                    airport)
                airportService.increaseOccupiedParkingSpaces(airport)
                logger.info { "A new transaction was added for license plate "+ licensePlate + " @ " + LocalDateTime.now() }
                logger.info { "Barrier RAISED for license plate "+ licensePlate + " @ " + LocalDateTime.now() }
                responseMap.put("result",true)
            }

        }else{
            logger.info { "Invalid car license plate " + licensePlate + " @ " + LocalDateTime.now() }
            responseMap.put("result",false)
        }

        return ResponseEntity.ok(responseMap)
    }

}