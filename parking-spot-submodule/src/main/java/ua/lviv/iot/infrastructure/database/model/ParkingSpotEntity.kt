package ua.lviv.iot.infrastructure.database.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.lviv.iot.domain.enum.ParkingSpotSize

@Document("parking_spot")
data class ParkingSpotEntity(
    val isAvailable: Boolean,
    val size: ParkingSpotSize
) {
    @Id
    lateinit var id: String
}
