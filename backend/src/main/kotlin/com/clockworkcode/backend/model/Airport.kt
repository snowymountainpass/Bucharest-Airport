package com.clockworkcode.backend.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.*

@Entity
data class Airport(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @OneToMany(mappedBy = "airport", cascade = [CascadeType.ALL], orphanRemoval = true)
    val parkingSpace: MutableList<ParkingSpace> = mutableListOf(),

    var airportName:String,
    var airportCode:String,
    var airportCostPerMinute:Int,
)
