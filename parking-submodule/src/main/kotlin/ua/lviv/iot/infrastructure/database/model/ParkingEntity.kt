package ua.lviv.iot.infrastructure.database.model


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("parking")
data class ParkingEntity(
    val location: String,
    val tradeNetwork: String,
    val countOfParkingSpots: Int
) {
    @Id
    lateinit var id: String
}
