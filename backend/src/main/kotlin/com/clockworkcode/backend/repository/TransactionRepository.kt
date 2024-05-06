package com.clockworkcode.backend.repository

import com.clockworkcode.backend.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {

    fun findTransactionByCarLicensePlate(licensePlate: String): Transaction?
    fun findTransactionByEntryTime(entryTime: Date): Transaction?
    fun findTransactionByDepartureTime(departureTime: Date): Transaction?
    fun findTransactionsByPaid(paid: Boolean): Collection<Transaction>
    fun findTransactionsByAirportName(airportName:String)
}