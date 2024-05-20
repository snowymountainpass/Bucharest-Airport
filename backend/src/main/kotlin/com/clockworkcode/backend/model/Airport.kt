package com.clockworkcode.backend.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
data class Airport(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airport_seq")
    @SequenceGenerator(name = "airport_seq", sequenceName = "airport_sequence", allocationSize = 1)
    internal val id: Long? = null,
    internal var airportName:String,
    internal var airportCode:String,
    internal var airportCostPerMinute:Int,
    internal var numberOfOccupiedParkingSpaces:Int,
    internal var numberOfParkingSpaces:Int,

    @OneToMany(mappedBy = "airport", cascade = [CascadeType.ALL], orphanRemoval = true)
    internal val transaction: MutableList<Transaction>,
)
