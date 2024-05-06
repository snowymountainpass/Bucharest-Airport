package com.clockworkcode.backend.model

import jakarta.persistence.*
import java.util.*

@Entity
data class ParkingSpace(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    var parkingSpaceName: String,

    @OneToOne(mappedBy = "parkingSpace")
    val transaction: Transaction,

    @ManyToOne
    @JoinColumn(name = "airport_id")
    val airport: Airport,

    var isOccupied:Boolean = false
    )
