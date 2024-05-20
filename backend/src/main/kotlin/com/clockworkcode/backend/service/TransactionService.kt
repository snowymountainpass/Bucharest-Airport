package com.clockworkcode.backend.service

import com.clockworkcode.backend.model.Airport
import com.clockworkcode.backend.model.Transaction
import com.clockworkcode.backend.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.abs

@Service
class TransactionService @Autowired constructor(private val transactionRepository: TransactionRepository) {

    fun addTransaction(
        carLicensePlate: String,
        airport: Airport
    )
    {
        val transaction = Transaction(
            carLicensePlate=carLicensePlate,
            entryTime=LocalDateTime.now(),
            departureTime=null,
            cost=0,
            transactionIsPaid=false,
            airport=airport
        )

        transactionRepository.save(transaction);
    }

    fun setDepartureTime(transaction: Transaction){
        //add 5 min to the departure time to account for the time it takes to exit the parking lot
        transaction.departureTime=LocalDateTime.now().plus(Duration.of(5, ChronoUnit.MINUTES))
        transactionRepository.save(transaction)
    }

    fun getTransactionByLicensePlate(carLicensePlate: String): Transaction {
        return transactionRepository.findTransactionByCarLicensePlate(carLicensePlate)!!
    }

    fun calculateAmountToBePaid(transaction: Transaction): Long {
        val durationInMinutes:Long = abs(Duration.between(transaction.entryTime,transaction.departureTime).toMinutes())
        val costPerAirport:Long = abs(transaction.cost)
        return durationInMinutes*costPerAirport
    }

    fun setCost(transaction: Transaction){
        transaction.cost=calculateAmountToBePaid(transaction)
        transactionRepository.save(transaction)
    }

}