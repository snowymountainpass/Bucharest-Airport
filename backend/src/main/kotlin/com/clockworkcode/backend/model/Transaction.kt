package com.clockworkcode.backend.model

import jakarta.persistence.*
import java.lang.reflect.Constructor
import java.time.LocalDateTime

import java.util.*

@Entity
data class Transaction(
    @Id
    val id:UUID,
    var carLicensePlate: String,
    var entryTime: LocalDateTime,
    var departureTime: LocalDateTime?,
    var cost: Long,
    var isPaid:Boolean = false,

    @ManyToOne
    @JoinColumn(name = "airport_id")
    val airport: Airport,

    )
