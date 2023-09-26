package ua.lviv.iot.parkingServer.repository

import reactor.core.publisher.Flux
import ua.lviv.iot.parkingServer.model.Parking

interface ParkingRepository : GeneralRepository<Parking> {
    fun findParkingByLocation(location: String): Flux<Parking>

    fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int): Flux<Parking>

    fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking>

}
