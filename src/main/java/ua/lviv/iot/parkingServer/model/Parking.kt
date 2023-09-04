package ua.lviv.iot.parkingServer.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("parking")
data class Parking(
    @Id
    var id: String = ObjectId.get().toString(),
    val location: String,
    val tradeNetwork: String,
    val countOfParkingSpots: Int
)
