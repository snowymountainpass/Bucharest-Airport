package com.clockworkcode.backend.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.util.*

@Entity
data class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    var carLicensePlate: String,
    var startTime: Date,
    var endTime: Date,
    @OneToOne
    @JoinColumn(name = "parking_space_id") //FK
    val parkingSpace:ParkingSpace,

    var cost: Long,
    var isPaid:Boolean = false
)
