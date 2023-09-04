package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.repository.VehicleMongoRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Service
class VehicleService(private val vehicleMongoRepository: VehicleMongoRepository) : VehicleServiceInterface {
    override fun findAllEntities(): List<Vehicle> = vehicleMongoRepository.findAll()

    override fun findEntityById(id: Long): Vehicle =
        vehicleMongoRepository.findById(id).orElseThrow { EntityNotFoundException("Vehicle with id=$id not found") }

    override fun addEntity(entity: Vehicle): Vehicle = vehicleMongoRepository.save(entity)

    override fun updateEntity(id: Long, entity: Vehicle): Vehicle {
        entity.id = findEntityById(id).id
        addEntity(entity)
        return addEntity(entity)
    }

    override fun deleteEntity(id: Long) = vehicleMongoRepository.deleteById(id)

}
