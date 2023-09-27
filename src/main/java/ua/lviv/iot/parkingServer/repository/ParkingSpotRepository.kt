package ua.lviv.iot.parkingServer.repository

import reactor.core.publisher.Flux
import ua.lviv.iot.parkingServer.model.ParkingSpot


interface ParkingSpotRepository : GeneralRepository<ParkingSpot> {

    fun findParkingSpotByAvailability(isAvailable : Boolean) : Flux<ParkingSpot>
}
