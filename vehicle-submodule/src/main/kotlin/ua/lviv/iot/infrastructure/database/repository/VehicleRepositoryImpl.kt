package ua.lviv.iot.infrastructure.database.repository

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.infrastructure.mapper.domainToEntity
import ua.lviv.iot.infrastructure.mapper.entityToDomain
import ua.lviv.iot.application.repository.VehicleRepositoryOutPort
import ua.lviv.iot.domain.Vehicle
import ua.lviv.iot.infrastructure.database.model.VehicleEntity

@Repository
class VehicleRepositoryImpl(private val reactiveMongoTemplate: ReactiveMongoTemplate) : VehicleRepositoryOutPort {

    override fun findAll(): Flux<Vehicle> {
        return reactiveMongoTemplate.findAll(VehicleEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun findById(id: String): Mono<Vehicle> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.findOne(query, VehicleEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun save(entity: Vehicle): Mono<Vehicle> {
        return reactiveMongoTemplate.save(entity.domainToEntity())
            .map { it.entityToDomain() }
    }

    override fun update(entity: Vehicle): Mono<Vehicle> {
        return reactiveMongoTemplate.save(entity.domainToEntity())
            .map { it.entityToDomain() }
    }

    override fun deleteById(id: String): Mono<Void> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.remove(query, VehicleEntity::class.java).then()
    }

    override fun findVehicleByNumber(number: String): Mono<Vehicle> {
        val query = Query()
            .addCriteria(Criteria.where("number").`is`(number))
        return reactiveMongoTemplate.findOne(query, VehicleEntity::class.java)
            .map { it.entityToDomain() }
    }
}
