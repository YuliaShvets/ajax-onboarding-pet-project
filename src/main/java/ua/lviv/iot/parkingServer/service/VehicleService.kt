package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.repository.VehicleRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepository
) : VehicleServiceInterface {
    override fun findAllEntities(): List<Vehicle> = vehicleRepository.findAll()

    override fun findEntityById(id: String): Vehicle =
        vehicleRepository.findById(id).orElseThrow { EntityNotFoundException("Vehicle with id=$id not found") }

    override fun addEntity(entity: Vehicle): Vehicle = vehicleRepository.save(entity)

    override fun updateEntity(entity: Vehicle): Vehicle {
        return vehicleRepository.save(entity)
    }

    override fun deleteEntity(id: String): Unit = vehicleRepository.deleteById(id)

}
