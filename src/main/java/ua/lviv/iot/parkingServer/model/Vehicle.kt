package ua.lviv.iot.parkingServer.model

import java.time.Duration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.parkingServer.model.enums.VehicleType

@Document("vehicle")
data class Vehicle(
    @Id
    val id: Long,
    val number: String,
    val typeOfVehicle: VehicleType,
    val durationOfUseOfParkingSpot: Duration,
    val isTicketReceived: Boolean
)
