package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.repository.VehicleMongoRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Service
class VehicleService(
    private val vehicleMongoRepository: VehicleMongoRepository
) : VehicleServiceInterface {
    override fun findAllEntities(): List<Vehicle> = vehicleMongoRepository.findAll()

    override fun findEntityById(id: String): Vehicle =
        vehicleMongoRepository.findById(id).orElseThrow { EntityNotFoundException("Vehicle with id=$id not found") }

    override fun addEntity(entity: Vehicle): Vehicle = vehicleMongoRepository.save(entity)

    override fun updateEntity(id: String, entity: Vehicle): Vehicle {
        entity.id = findEntityById(id).id
        return addEntity(entity)
    }

    override fun deleteEntity(id: String): Unit = vehicleMongoRepository.deleteById(id)

}
