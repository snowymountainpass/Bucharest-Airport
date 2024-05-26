package com.clockworkcode.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", allocationSize = 1)
    internal val id:Long? = null,
    internal val carLicensePlate: String,
    internal val entryTime: LocalDateTime,
    internal var departureTime: LocalDateTime?,
    internal var cost: Int,
    internal var transactionIsPaid:Boolean = false,

    @ManyToOne
    @JoinColumn(name = "airport_id")
    internal val airport: Airport,



    ) {
    override fun toString(): String {
        return "Transaction(id=$id, carLicensePlate='$carLicensePlate', entryTime=$entryTime, departureTime=$departureTime, cost=$cost, transactionIsPaid=$transactionIsPaid, airport=$airport)"
    }
}
