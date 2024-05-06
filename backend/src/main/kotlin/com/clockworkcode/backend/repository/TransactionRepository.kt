package com.clockworkcode.backend.repository

import com.clockworkcode.backend.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {

    fun findTransactionByCarLicensePlate(licensePlate: String): Transaction?
    fun findTransactionByParkingSpace(parkingSpaceName: String): Transaction?
    fun findTransactionByParkingSpaceName(parkingSpaceId: String): Transaction?
    fun findTransactionByStartTime(startTime: Date): Transaction?
    fun findTransactionByEndTime(endTime: Date): Transaction?
    fun findTransactionsByPaid(paid: Boolean): Collection<Transaction>
}