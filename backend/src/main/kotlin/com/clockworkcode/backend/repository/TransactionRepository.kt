package com.clockworkcode.backend.repository

import com.clockworkcode.backend.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {

    fun findTransactionByCarLicensePlate(licensePlate: String): Transaction?
    fun findTransactionByEntryTime(entryTime: LocalDateTime): Transaction?
    fun findTransactionByDepartureTime(departureTime: LocalDateTime): Transaction?
    fun findTransactionsByCarLicensePlateOrderByDepartureTimeDesc(licensePlate: String): List<Transaction>?
    fun findTransactionsByTransactionIsPaid(paid: Boolean): Collection<Transaction>
    fun findTransactionsByAirport_AirportName(airportName:String)

    // Most recent unpaid transaction for a certain license plate
    fun findTransactionByCarLicensePlateAndTransactionIsPaid(carLicensePlate: String,isPaid:Boolean): Transaction?;

}