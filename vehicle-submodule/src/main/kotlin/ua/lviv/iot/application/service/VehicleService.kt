package ua.lviv.iot.application.service

import com.mongodb.client.result.DeleteResult
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.application.repository.VehicleRepositoryOutPort
import ua.lviv.iot.domain.Vehicle
import ua.lviv.iot.exception.EntityNotFoundException

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepositoryOutPort
) : VehicleInPort {

    override fun findAllEntities(): Flux<Vehicle> = vehicleRepository.findAll()

    override fun findEntityById(id: String): Mono<Vehicle> =
        vehicleRepository.findById(id)
            .switchIfEmpty(Mono.error(EntityNotFoundException("Vehicle with id=$id not found")))

    override fun addEntity(entity: Vehicle): Mono<Vehicle> = vehicleRepository.save(entity)

    override fun updateEntity(entity: Vehicle): Mono<Vehicle> {
        return vehicleRepository.update(entity)
    }

    override fun deleteEntity(id: String): Mono<Void> = vehicleRepository.deleteById(id)

    override fun findVehicleByNumber(number: String): Mono<Vehicle> {
        return vehicleRepository.findVehicleByNumber(number)
    }

}
