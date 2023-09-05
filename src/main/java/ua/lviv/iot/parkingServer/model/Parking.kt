package ua.lviv.iot.parkingServer.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("parking")
data class Parking(
    var location: String,
    var tradeNetwork: String,
    var countOfParkingSpots: Int
) {
    @Id
    lateinit var id: String
}
