package ua.lviv.iot.parkingServer.model

import java.time.Duration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.parkingServer.model.enums.VehicleType

@Document("vehicle")
data class Vehicle(
    var number: String,
    var typeOfVehicle: VehicleType,
    var durationOfUseOfParkingSpot: Duration,
    var isTicketReceived: Boolean
) {
    @Id
    lateinit var id: String
}
