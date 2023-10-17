package ua.lviv.iot.infrastructure.database.repository

import ua.lviv.iot.application.repository.ParkingRepositoryOutPort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.infrastructure.mapper.domainToEntity
import ua.lviv.iot.infrastructure.mapper.entityToDomain
import ua.lviv.iot.domain.Parking
import ua.lviv.iot.infrastructure.database.model.ParkingEntity

@Repository
class ParkingRepository(val reactiveMongoTemplate: ReactiveMongoTemplate) : ParkingRepositoryOutPort {

    override fun findAll(): Flux<Parking> {
        return reactiveMongoTemplate.findAll(ParkingEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun findById(id: String): Mono<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.findOne(query, ParkingEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun save(entity: Parking): Mono<Parking> {
        return reactiveMongoTemplate.save(entity.domainToEntity())
            .map { it.entityToDomain() }
    }

    override fun update(entity: Parking): Mono<Parking> {
        return reactiveMongoTemplate.save(entity.domainToEntity())
            .map { it.entityToDomain() }
    }

    override fun deleteById(id: String): Mono<Void> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.remove(query, ParkingEntity::class.java).then()
    }

    override fun findParkingByLocation(location: String): Flux<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("location").`is`(location))
        return reactiveMongoTemplate.find(query, ParkingEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int): Flux<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("countOfParkingSpots").gt(countOfParkingSpot))
        return reactiveMongoTemplate.find(query, ParkingEntity::class.java)
            .map { it.entityToDomain() }
    }

    override fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("tradeNetwork").`is`(tradeNetwork))
        return reactiveMongoTemplate.find(query, ParkingEntity::class.java)
            .map { it.entityToDomain() }
    }
}
