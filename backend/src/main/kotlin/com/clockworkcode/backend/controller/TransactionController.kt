package com.clockworkcode.backend.controller

import com.clockworkcode.backend.dto.PaymentDTO
import com.clockworkcode.backend.service.AirportService
import com.clockworkcode.backend.service.TransactionService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.Date

@RestController
@RequestMapping("/transaction")
@CrossOrigin
class TransactionController @Autowired constructor(
    private val transactionService: TransactionService,
    private val airportService: AirportService
    ) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/addTransaction")
    fun addTransaction(paymentDTO: PaymentDTO): ResponseEntity<Map<String, Boolean>>{
        var responseMap:Map<String, Boolean> = HashMap()
        //TODO: add validator for license plate

        var isValidCarLicensePlate:Boolean=true
        if (isValidCarLicensePlate){
            val airport = airportService.getAirportByAirportName(paymentDTO.airportName)
            transactionService.addTransaction(
                paymentDTO.carLicensePlate,
                paymentDTO.entryTime,
                paymentDTO.departureTime,
                paymentDTO.cost,
                paymentDTO.isPaid,
                airport)
                airportService.increaseOccupiedParkingSpaces(airport)
                logger.info { "A new transaction was added for license plate "+ paymentDTO.carLicensePlate + " @ " + LocalDateTime.now() }
        }else{
            logger.info { "Invalid car license plate " + paymentDTO.carLicensePlate + " @ " + LocalDateTime.now() }
        }

        return ResponseEntity.ok(responseMap)
    }

}