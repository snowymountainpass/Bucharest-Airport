package com.clockworkcode.backend.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Airport(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    internal val id: UUID,
    internal var airportName:String,
    internal var airportCode:String,
    internal var airportCostPerMinute:Int,
    internal var numberOfOccupiedParkingSpaces:Int,
    internal var numberOfParkingSpaces:Int,

    @OneToMany(mappedBy = "airport", cascade = [CascadeType.ALL], orphanRemoval = true)
    internal val transaction: MutableList<Transaction>,
)
