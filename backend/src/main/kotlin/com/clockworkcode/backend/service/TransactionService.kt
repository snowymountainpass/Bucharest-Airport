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
            cost=airport.airportCostPerMinute,
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
        return transactionRepository.findTransactionByCarLicensePlateAndTransactionIsPaid(carLicensePlate,false)!!
    }

    fun calculateAmountToBePaid(transaction: Transaction): Int {
        val durationInMinutes:Int = abs(Duration.between(transaction.entryTime,transaction.departureTime).toMinutes()).toInt()
        val costPerAirport:Int = abs(transaction.airport.airportCostPerMinute)
        return durationInMinutes*costPerAirport
    }

    fun setCost(transaction: Transaction){
        transaction.cost=calculateAmountToBePaid(transaction)
        transactionRepository.save(transaction)
    }

    fun setIsPaid(transaction: Transaction){
        transaction.transactionIsPaid=true
        transactionRepository.save(transaction)
    }

    fun getUnpaidTransaction(carLicensePlate: String): Transaction? {
        val transaction: Transaction? =
            transactionRepository.findTransactionByCarLicensePlateAndTransactionIsPaid(carLicensePlate,false)

        return transaction
    }

    fun findLatestTransactionForLicensePlate(licensePlate: String): Transaction? {
        val licensePlateTransactions:List<Transaction> = transactionRepository.findTransactionsByCarLicensePlateOrderByDepartureTimeDesc(licensePlate)!!
        if(licensePlateTransactions.isNotEmpty()){
            return licensePlateTransactions.first()
        }
        return null
    }

}