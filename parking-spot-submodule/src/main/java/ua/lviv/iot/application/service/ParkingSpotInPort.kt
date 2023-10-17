package ua.lviv.iot.application.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.ParkingSpot

interface ParkingSpotInPort {

    fun findAllEntities(): Flux<ParkingSpot>

    fun findEntityById(id: String): Mono<ParkingSpot>

    fun addEntity(entity : ParkingSpot): Mono<ParkingSpot>

    fun updateEntity(entity: ParkingSpot): Mono<ParkingSpot>

    fun deleteEntity(id : String): Mono<Void>

    fun findParkingSpotByAvailability(isAvailable : Boolean) : Flux<ParkingSpot>
}
