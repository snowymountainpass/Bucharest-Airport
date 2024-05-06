package com.clockworkcode.backend.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Airport(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    var airportName:String,
    var airportCode:String,
    var airportCostPerMinute:Int,
    var numberOfOccupiedParkingSpaces:Int,
    var numberOfParkingSpaces:Int,

    @OneToMany(mappedBy = "airport", cascade = [CascadeType.ALL], orphanRemoval = true)
    val transaction: MutableList<Transaction>,
)
