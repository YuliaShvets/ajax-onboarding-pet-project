package ua.lviv.iot.parkingServer.service.interfaces

import reactor.core.publisher.Flux
import ua.lviv.iot.parkingServer.model.ParkingSpot

interface ParkingSpotServiceInterface : GeneralServiceInterface<ParkingSpot, String> {

    fun findParkingSpotByAvailability(isAvailable: Boolean): Flux<ParkingSpot>
}
