package com.clockworkcode.backend.service

import com.clockworkcode.backend.model.Airport
import com.clockworkcode.backend.model.Transaction
import com.clockworkcode.backend.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class TransactionService @Autowired constructor(private val transactionRepository: TransactionRepository) {

    fun addTransaction(
        carLicensePlate: String,
        entryTime: LocalDateTime,
        departureTime: LocalDateTime?=null,
        cost: Long=0,
        isPaid:Boolean=false,
        airport: Airport
    ):Transaction
    {
        val transaction = Transaction(
            id = UUID.randomUUID(),
            carLicensePlate=carLicensePlate,
            entryTime=entryTime,
            departureTime=departureTime,
            cost=cost,
            isPaid=isPaid,
            airport=airport
        )
        airport.numberOfOccupiedParkingSpaces++
        return transactionRepository.save(transaction);
    }

    fun setDepartureTime(transaction: Transaction){
        transaction.departureTime=LocalDateTime.now()
    }

}