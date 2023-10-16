package ua.lviv.iot.application.service

import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.Vehicle

interface VehicleServiceInPort {

    fun findAllEntities(): Flux<Vehicle>

    fun findEntityById(id: String): Mono<Vehicle>

    fun addEntity(entity: Vehicle): Mono<Vehicle>

    fun updateEntity(entity: Vehicle): Mono<Vehicle>

    fun deleteEntity(id: String): Mono<DeleteResult>

    fun findVehicleByNumber(number: String): Mono<Vehicle>
}
