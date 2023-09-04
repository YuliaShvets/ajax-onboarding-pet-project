package ua.lviv.iot.parkingServer.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

@Document("parking_spot")
data class ParkingSpot(
    @Id
    var id: String = ObjectId.get().toString(),
    val isAvailable: Boolean,
    val size: ParkingSpotSize
)
