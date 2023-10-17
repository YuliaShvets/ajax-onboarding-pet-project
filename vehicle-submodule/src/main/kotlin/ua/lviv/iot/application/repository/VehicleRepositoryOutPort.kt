package ua.lviv.iot.application.repository

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.domain.Vehicle

interface VehicleRepositoryOutPort  {

    fun findAll(): Flux<Vehicle>

    fun findById(id: String): Mono<Vehicle>

    fun save(entity : Vehicle): Mono<Vehicle>

    fun update(entity: Vehicle): Mono<Vehicle>

    fun deleteById(id : String): Mono<Void>

    fun findVehicleByNumber(number: String) : Mono<Vehicle>
}
