package ua.lviv.iot.application.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.Parking

interface ParkingInPort  {
    fun findAllEntities(): Flux<Parking>

    fun findEntityById(id: String): Mono<Parking>

    fun addEntity(entity: Parking): Mono<Parking>

    fun updateEntity(entity: Parking): Mono<Parking>

    fun deleteEntity(id: String) : Mono<Void>

    fun findParkingByLocation(location: String): Flux<Parking>

    fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int) : Flux<Parking>

    fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking>
}
