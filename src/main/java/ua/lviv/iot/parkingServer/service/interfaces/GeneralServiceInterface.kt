package ua.lviv.iot.parkingServer.service.interfaces

import com.mongodb.client.result.DeleteResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface GeneralServiceInterface<T, ID> {

    fun findAllEntities(): Flux<T>

    fun findEntityById(id: String): Mono<T>

    fun addEntity(entity: T): Mono<T>

    fun updateEntity(entity: T): Mono<T>

    fun deleteEntity(id: String) : Mono<DeleteResult>

}
