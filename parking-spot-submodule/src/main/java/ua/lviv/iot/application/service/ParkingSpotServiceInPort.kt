package ua.lviv.iot.application.service

import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.ParkingSpot

interface ParkingSpotServiceInPort {

    fun findAllEntities(): Flux<ParkingSpot>

    fun findEntityById(id: String): Mono<ParkingSpot>

    fun addEntity(entity : ParkingSpot): Mono<ParkingSpot>

    fun updateEntity(entity: ParkingSpot): Mono<ParkingSpot>

    fun deleteEntity(id : String): Mono<DeleteResult>

    fun findParkingSpotByAvailability(isAvailable : Boolean) : Flux<ParkingSpot>
}
