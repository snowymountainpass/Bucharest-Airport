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
        var transaction: Transaction? = transactionService.getTransactionByLicensePlate(carLicensePlate)
        if (transaction != null) {
            transactionService.setDepartureTime(transaction)
            transactionService.setCost(transaction)
        }
        var paymentDTO = PaymentDTO(
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
    fun checkout(@RequestBody paymentDTO: PaymentDTO): ResponseEntity<Map<String, String>> {
        val paymentDto= getPaymentDetails(paymentDTO.carLicensePlate)
        val session:Session = orderService.createSession(paymentDto)
        // Prepare response map with client secret
        val responseMap:HashMap<String,String> = HashMap()
        responseMap.put("clientSecret",session.getClientSecret())
        airportService.decreaseOccupiedParkingSpaces(airportService.getAirportByAirportName(paymentDto.airportName))

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/session-status")
    fun getSessionStatus(@RequestParam("session_id") sessionId:String): ResponseEntity<Map<String, String>> {
        // Retrieve the session from Stripe using the session ID
        val session:Session = Session.retrieve(sessionId)

        // Extract status and customer email from the session
        val status:String = session.status
        val customerEmail:String = session.customerEmail

        // Prepare response map with status and customer email
        val responseMap = HashMap<String,String>()
        responseMap.put("status", status)
        responseMap.put("customerEmail", customerEmail)

        return ResponseEntity.ok(responseMap)
    }
}