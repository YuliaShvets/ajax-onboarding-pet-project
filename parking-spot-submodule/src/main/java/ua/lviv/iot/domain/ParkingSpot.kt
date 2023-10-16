package ua.lviv.iot.domain

import ua.lviv.iot.domain.enum.ParkingSpotSize

data class ParkingSpot(
    val id: String?,
    val isAvailable: Boolean,
    val size: ParkingSpotSize
)
