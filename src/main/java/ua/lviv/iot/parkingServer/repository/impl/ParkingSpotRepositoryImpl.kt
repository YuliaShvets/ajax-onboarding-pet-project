package ua.lviv.iot.parkingServer.repository.impl

import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.repository.ParkingSpotRepository

@Repository
class ParkingSpotRepositoryImpl(private val reactiveMongoTemplate: ReactiveMongoTemplate) : ParkingSpotRepository {

    override fun findAll(): Flux<ParkingSpot> {
        return reactiveMongoTemplate.findAll(ParkingSpot::class.java)
    }

    override fun findById(id: String): Mono<ParkingSpot> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.findOne(query, ParkingSpot::class.java)
    }

    override fun save(entity: ParkingSpot): Mono<ParkingSpot> {
        return reactiveMongoTemplate.save(entity)
    }

    override fun update(entity: ParkingSpot): Mono<ParkingSpot> {
        return reactiveMongoTemplate.save(entity)
    }

    override fun deleteById(id: String): Mono<DeleteResult> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.remove(query, ParkingSpot::class.java)
    }

    override fun findParkingSpotByAvailability(isAvailable: Boolean): Flux<ParkingSpot> {
        val query = Query()
            .addCriteria(Criteria.where("isAvailable").`is`(isAvailable))
        return reactiveMongoTemplate.find(query, ParkingSpot::class.java)
    }
}
