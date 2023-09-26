package ua.lviv.iot.parkingServer.repository.impl

import com.mongodb.client.result.DeleteResult
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.repository.VehicleRepository

@Repository
class VehicleRepositoryImpl(private val reactiveMongoTemplate: ReactiveMongoTemplate) : VehicleRepository {

    override fun findAll(): Flux<Vehicle> {
        return reactiveMongoTemplate.findAll(Vehicle::class.java)
    }

    override fun findById(id: String): Mono<Vehicle> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.findOne(query, Vehicle::class.java)
    }

    override fun save(entity: Vehicle): Mono<Vehicle> {
        return reactiveMongoTemplate.save(entity)
    }

    override fun update(entity: Vehicle): Mono<Vehicle> {
        return reactiveMongoTemplate.save(entity)
    }

    override fun deleteById(id: String): Mono<DeleteResult> {
        val query = Query()
            .addCriteria(Criteria.where("_id").`is`(id))
        return reactiveMongoTemplate.remove(query, Vehicle::class.java)
    }

    override fun findVehicleByNumber(number: String): Mono<Vehicle> {
        val query = Query()
            .addCriteria(Criteria.where("number").`is`(number))
        return reactiveMongoTemplate.findOne(query, Vehicle::class.java)
    }
}
