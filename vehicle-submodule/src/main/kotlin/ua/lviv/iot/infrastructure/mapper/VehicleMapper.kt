package ua.lviv.iot.infrastructure.mapper

import ua.lviv.iot.domain.Vehicle
import ua.lviv.iot.infrastructure.database.model.VehicleEntity

fun VehicleEntity.entityToDomain(): Vehicle {
    return Vehicle(
        id = this.id,
        number = this.number,
        typeOfVehicle = this.typeOfVehicle,
        durationOfUseOfParkingSpot = this.durationOfUseOfParkingSpot,
        isTicketReceived = this.isTicketReceived
    )
}

fun Vehicle.domainToEntity(): VehicleEntity {
    return VehicleEntity(
        number = this.number,
        typeOfVehicle = this.typeOfVehicle,
        durationOfUseOfParkingSpot = this.durationOfUseOfParkingSpot,
        isTicketReceived = this.isTicketReceived
    )
}
