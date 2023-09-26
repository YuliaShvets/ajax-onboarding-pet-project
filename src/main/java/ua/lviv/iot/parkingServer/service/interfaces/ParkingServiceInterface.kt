package ua.lviv.iot.parkingServer.service.interfaces

import reactor.core.publisher.Flux
import ua.lviv.iot.parkingServer.model.Parking

interface ParkingServiceInterface : GeneralServiceInterface<Parking, String> {
    fun findParkingByLocation(location: String): Flux<Parking>

    fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int) : Flux<Parking>

    fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking>
}
