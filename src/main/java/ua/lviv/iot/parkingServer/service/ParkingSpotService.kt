package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.repository.ParkingSpotMongoRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.service.interfaces.ParkingSpotServiceInterface

@Service
class ParkingSpotService(private val parkingSpotMongoRepository: ParkingSpotMongoRepository) :
    ParkingSpotServiceInterface {
    override fun findAllEntities(): List<ParkingSpot> = parkingSpotMongoRepository.findAll()

    override fun findEntityById(id: Long): ParkingSpot = parkingSpotMongoRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Parking spot with id=$id not found") }

    override fun addEntity(entity: ParkingSpot): ParkingSpot = parkingSpotMongoRepository.save(entity)

    override fun updateEntity(id: Long, entity: ParkingSpot): ParkingSpot {
        entity.id = findEntityById(id).id
        addEntity(entity)
        return addEntity(entity)
    }

    override fun deleteEntity(id: Long) = parkingSpotMongoRepository.deleteById(id)

}
