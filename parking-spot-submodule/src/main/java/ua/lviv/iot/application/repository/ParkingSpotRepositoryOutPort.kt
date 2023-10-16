package ua.lviv.iot.application.repository

import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.ParkingSpot

interface ParkingSpotRepositoryOutPort  {

    fun findAll(): Flux<ParkingSpot>

    fun findById(id: String): Mono<ParkingSpot>

    fun save(entity : ParkingSpot): Mono<ParkingSpot>

    fun update(entity: ParkingSpot): Mono<ParkingSpot>

    fun deleteById(id : String): Mono<DeleteResult>

    fun findParkingSpotByAvailability(isAvailable : Boolean) : Flux<ParkingSpot>
}
