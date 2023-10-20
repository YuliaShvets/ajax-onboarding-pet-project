package ua.lviv.iot.infrastructure.mapper

import ua.lviv.iot.domain.ParkingSpot
import ua.lviv.iot.infrastructure.database.model.ParkingSpotEntity

fun ParkingSpotEntity.entityToDomain(): ParkingSpot {
    return ParkingSpot(
        id = this.id,
        isAvailable = this.isAvailable,
        size = this.size
    )
}

fun ParkingSpot.domainToEntity(): ParkingSpotEntity {
    return ParkingSpotEntity(
        isAvailable = this.isAvailable,
        size = this.size
    )
}
