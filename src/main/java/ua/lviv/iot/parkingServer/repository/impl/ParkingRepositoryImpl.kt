package ua.lviv.iot.parkingServer.repository.impl

import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.repository.ParkingRepository

@Repository
class ParkingRepositoryImpl(val reactiveMongoTemplate: ReactiveMongoTemplate) : ParkingRepository {

    override fun findAll(): Flux<Parking> {
        return reactiveMongoTemplate.findAll(Parking::class.java)
    }

    override fun findById(id: String): Mono<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.findOne(query, Parking::class.java)
    }

    override fun save(entity: Parking): Mono<Parking> {
        return reactiveMongoTemplate.save(entity)
    }

    override fun update(entity: Parking): Mono<Parking> {
        return reactiveMongoTemplate.save(entity)
    }

    override fun deleteById(id: String): Mono<DeleteResult> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
       return reactiveMongoTemplate.remove(query, Parking::class.java)
    }

    override fun findParkingByLocation(location: String): Flux<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("location").`is`(location))
        return reactiveMongoTemplate.find(query, Parking::class.java)
    }

    override fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int): Flux<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("countOfParkingSpots").gt(countOfParkingSpot))
        return reactiveMongoTemplate.find(query, Parking::class.java)
    }

    override fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("tradeNetwork").`is`(tradeNetwork))
        return reactiveMongoTemplate.find(query, Parking::class.java)
    }
}
