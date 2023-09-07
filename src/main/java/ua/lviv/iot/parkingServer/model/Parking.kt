package ua.lviv.iot.parkingServer.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("parking")
data class Parking(
    val location: String,
    val tradeNetwork: String,
    val countOfParkingSpots: Int
) {
    @Id
    lateinit var id: String
}
