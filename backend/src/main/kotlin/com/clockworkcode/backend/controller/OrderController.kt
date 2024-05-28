package com.clockworkcode.backend.controller

import com.clockworkcode.backend.dto.PaymentDTO
import com.clockworkcode.backend.model.Transaction
import com.clockworkcode.backend.service.AirportService
import com.clockworkcode.backend.service.OrderService
import com.clockworkcode.backend.service.TransactionService
import com.stripe.model.checkout.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
@CrossOrigin
class OrderController @Autowired constructor(val orderService: OrderService,
                                             val transactionService: TransactionService,
                                             val airportService: AirportService)
{
    fun getPaymentDetails(carLicensePlate:String): PaymentDTO  {
        val transaction: Transaction? = transactionService.getTransactionByLicensePlate(carLicensePlate)
        if (transaction != null) {
            transactionService.setDepartureTime(transaction)
            transactionService.setCost(transaction)
        }
        val paymentDTO = PaymentDTO(
            transaction!!.carLicensePlate,
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
        val paymentDto:PaymentDTO= getPaymentDetails(requestBody["licensePlate"]!!)
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

        // Extract status and customer email from the session
        val status:String = session.status
//        val customerEmail:String = session.customerEmail


        // Prepare response map with status and customer email
        val responseMap = HashMap<String,String>()
        responseMap.put("status", status)
//        responseMap.put("customerEmail", customerEmail)

        return ResponseEntity.ok(responseMap)
    }

    @PostMapping("/payment-status")
    fun setPaymentStatus(@RequestBody requestBody: Map<String, String>): ResponseEntity<Map<String, Boolean>> {
        val responseMap:HashMap<String,Boolean> = HashMap()
        val licensePlate:String= requestBody["licensePlate"]!!
        val transaction = transactionService.getTransactionByLicensePlate(licensePlate)
        transactionService.setIsPaid(transaction)

        responseMap.put("isPaid", true)

        return ResponseEntity.ok(responseMap)

    }

}