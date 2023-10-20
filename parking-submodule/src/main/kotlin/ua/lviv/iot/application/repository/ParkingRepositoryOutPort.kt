package ua.lviv.iot.application.repository

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.Parking

interface ParkingRepositoryOutPort  {

    fun findAll(): Flux<Parking>

    fun findById(id: String): Mono<Parking>

    fun save(entity : Parking): Mono<Parking>

    fun update(entity: Parking): Mono<Parking>

    fun deleteById(id : String): Mono<Unit>

    fun findParkingByLocation(location: String): Flux<Parking>

    fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int): Flux<Parking>

    fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking>

}
