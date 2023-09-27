package ua.lviv.iot.parkingServer.repository

import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GeneralRepository<T> {

    fun findAll(): Flux<T>

    fun findById(id: String): Mono<T>

    fun save(entity : T): Mono<T>

    fun update(entity: T): Mono<T>

    fun deleteById(id : String): Mono<DeleteResult>
}
