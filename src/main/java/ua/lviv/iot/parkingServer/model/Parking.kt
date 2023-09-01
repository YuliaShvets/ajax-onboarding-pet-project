package ua.lviv.iot.parkingServer.model

import org.springframework.data.mongodb.core.mapping.Document


@Document("parking")
data class Parking(
    val id: Long,
    val location: String,
    val tradeNetwork: String,
    val countOfParkingSpots: Int
)
