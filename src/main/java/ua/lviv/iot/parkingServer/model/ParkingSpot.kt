package ua.lviv.iot.parkingServer.model

import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

@Document("parking_spot")
data class ParkingSpot(
    val id: Long,
    val isAvailable: Boolean,
    val size: ParkingSpotSize
)
