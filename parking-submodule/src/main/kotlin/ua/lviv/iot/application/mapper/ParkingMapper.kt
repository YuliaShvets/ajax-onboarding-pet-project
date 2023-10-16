package ua.lviv.iot.application.mapper

import ua.lviv.iot.domain.Parking
import ua.lviv.iot.infrastructure.database.model.ParkingEntity


fun ParkingEntity.entityToDomain(): Parking {
    return Parking(
        id = this.id,
        location = this.location,
        tradeNetwork = this.tradeNetwork,
        countOfParkingSpots = this.countOfParkingSpots
    )
}

fun Parking.domainToEntity(): ParkingEntity {
    return ParkingEntity(
        location = this.location,
        tradeNetwork = this.tradeNetwork,
        countOfParkingSpots = this.countOfParkingSpots
    )
}
