package com.clockworkcode.backend.dto

import java.time.LocalDateTime

data class PaymentDTO(
    internal val carLicensePlate: String,
    internal val airportName: String,
    internal val entryTime: LocalDateTime,
    internal var departureTime: LocalDateTime?=null,
    internal var cost: Long,
    internal var isPaid:Boolean,
)