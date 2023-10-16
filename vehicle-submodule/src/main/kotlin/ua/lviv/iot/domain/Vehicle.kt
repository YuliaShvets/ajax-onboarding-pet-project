package ua.lviv.iot.domain

import java.time.Duration
import ua.lviv.iot.domain.enum.VehicleType

data class Vehicle(
    var id: String?,
    val number: String,
    val typeOfVehicle: VehicleType,
    val durationOfUseOfParkingSpot: Duration,
    val isTicketReceived: Boolean
)
