package ua.lviv.iot.infrastructure.database.model

import java.time.Duration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.domain.enum.VehicleType

@Document("vehicle")
data class VehicleEntity(
    val number: String,
    val typeOfVehicle: VehicleType,
    val durationOfUseOfParkingSpot: Duration,
    val isTicketReceived: Boolean
) {
    @Id
    lateinit var id: String
}
