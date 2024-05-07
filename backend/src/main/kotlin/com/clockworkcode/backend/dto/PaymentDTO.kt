package com.clockworkcode.backend.dto

import java.time.LocalDateTime

class PaymentDTO(
    val carLicensePlate: String,
    val airportName: String,
    val entryTime: LocalDateTime,
    val departureTime: LocalDateTime?,
    val cost: Long,
    val isPaid:Boolean,
)