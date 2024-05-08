package com.clockworkcode.backend.model

import jakarta.persistence.*
import java.lang.reflect.Constructor
import java.time.LocalDateTime

import java.util.*

@Entity
data class Transaction(
    @Id
    internal val id:UUID,
    internal val carLicensePlate: String,
    internal val entryTime: LocalDateTime,
    internal var departureTime: LocalDateTime?,
    internal var cost: Long,
    internal var isPaid:Boolean = false,

    @ManyToOne
    @JoinColumn(name = "airport_id")
    internal val airport: Airport,

    )
