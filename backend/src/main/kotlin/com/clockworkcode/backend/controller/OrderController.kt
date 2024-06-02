package com.clockworkcode.backend.controller

import com.clockworkcode.backend.dto.PaymentDTO
import com.clockworkcode.backend.model.Transaction
import com.clockworkcode.backend.service.AirportService
import com.clockworkcode.backend.service.OrderService
import com.clockworkcode.backend.service.TransactionService
import com.clockworkcode.backend.service.Utility
import com.stripe.model.checkout.Session
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping("/order")
@CrossOrigin
class OrderController @Autowired constructor(val orderService: OrderService,
                                             val transactionService: TransactionService,
                                             val airportService: AirportService)
{
    private val logger = KotlinLogging.logger {}
    val utility = Utility()

    fun getPaymentDetails(carLicensePlate:String): PaymentDTO  {

        val transaction: Transaction = transactionService.getTransactionByLicensePlate(carLicensePlate)
        transactionService.setDepartureTime(transaction)
        transactionService.setCost(transaction)
        val paymentDTO = PaymentDTO(
            transaction.carLicensePlate,
            transaction.airport.airportName,
            transaction.entryTime,
            transaction.departureTime,
            transaction.cost,
            transaction.transactionIsPaid
        )

        return paymentDTO
    }

    @PostMapping("/checkout")
    fun checkout(@RequestBody requestBody: Map<String, String>): ResponseEntity<Map<String, String>> {
        val licensePlate = requestBody["licensePlate"]

        //Check if the license plate contains any unwanted keywords
        if(!utility.isValidAgainstSQLInjection(licensePlate.toString().lowercase(Locale.getDefault()))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mapOf("status" to "Denied"))
        }

        val paymentDto:PaymentDTO= getPaymentDetails(licensePlate!!)
        val session:Session = orderService.createSession(paymentDto)
        // Prepare response map with client secret
        val responseMap:HashMap<String,String> = HashMap()
        responseMap["clientSecret"] = session.clientSecret
        airportService.decreaseOccupiedParkingSpaces(airportService.getAirportByAirportName(paymentDto.airportName))

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/session-status")
    fun getSessionStatus(@RequestParam("session_id") sessionId:String): ResponseEntity<Map<String, String>> {
        // Retrieve the session from Stripe using the session ID
        val session:Session = Session.retrieve(sessionId)
        val licensePlate:String = session.metadata["licensePlate"].toString()
        // Extract status and customer email from the session
        val status:String = session.status
        val customerEmail:String = session.customerDetails.email

        if(status == "complete"){
            val transaction = transactionService.findLatestTransactionForLicensePlate(licensePlate)
            if (transaction != null) {
                transactionService.setIsPaid(transaction)
            }
            logger.info { "The transaction for license plate "+ licensePlate + " has been paid at "+LocalDateTime.now()+"!"}
        }

        // Prepare response map with status, customer email & isPaid status
        val responseMap = HashMap<String,String>()
        responseMap.put("status", status)
        responseMap.put("customerEmail", customerEmail)
        responseMap.put("isPaid", "true")

        return ResponseEntity.ok(responseMap)
    }

}