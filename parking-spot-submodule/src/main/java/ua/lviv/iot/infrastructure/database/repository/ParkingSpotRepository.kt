package ua.lviv.iot.infrastructure.database.repository

import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.application.mapper.domainToEntity
import ua.lviv.iot.application.mapper.entityToDomain
import ua.lviv.iot.application.repository.ParkingSpotRepositoryOutPort
import ua.lviv.iot.domain.ParkingSpot
import ua.lviv.iot.infrastructure.database.model.ParkingSpotEntity

@Repository
class ParkingSpotRepository(private val reactiveMongoTemplate: ReactiveMongoTemplate) : ParkingSpotRepositoryOutPort {

    override fun findAll(): Flux<ParkingSpot> {
        return reactiveMongoTemplate.findAll(ParkingSpotEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun findById(id: String): Mono<ParkingSpot> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.findOne(query, ParkingSpotEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun save(entity: ParkingSpot): Mono<ParkingSpot> {
        return reactiveMongoTemplate.save(entity.domainToEntity())
            .map { it.entityToDomain() }
    }

    override fun update(entity: ParkingSpot): Mono<ParkingSpot> {
        return reactiveMongoTemplate.save(entity.domainToEntity())
            .map { it.entityToDomain() }
    }

    override fun deleteById(id: String): Mono<DeleteResult> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.remove(query, ParkingSpotEntity::class.java)
    }

    override fun findParkingSpotByAvailability(isAvailable: Boolean): Flux<ParkingSpot> {
        val query = Query()
            .addCriteria(Criteria.where("isAvailable").`is`(isAvailable))
        return reactiveMongoTemplate.find(query, ParkingSpotEntity::class.java)
            .map { it.entityToDomain() }
    }
}
