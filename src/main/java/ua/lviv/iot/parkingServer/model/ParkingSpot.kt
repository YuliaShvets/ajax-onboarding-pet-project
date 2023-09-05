package ua.lviv.iot.parkingServer.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

@Document("parking_spot")
data class ParkingSpot(
    var isAvailable: Boolean,
    var size: ParkingSpotSize
) {
    @Id
    lateinit var id: String
}
