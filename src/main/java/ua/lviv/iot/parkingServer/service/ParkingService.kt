package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.annotation.TrackMetrics
import ua.lviv.iot.parkingServer.repository.ParkingMongoRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@TrackMetrics
@Service
class ParkingService(
    private val parkingMongoRepository: ParkingMongoRepository
) : ParkingServiceInterface {
    override fun findAllEntities(): List<Parking> = parkingMongoRepository.findAll()

    override fun findEntityById(id: String): Parking = parkingMongoRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Parking with id=$id not found") }

    override fun addEntity(entity: Parking): Parking = parkingMongoRepository.save(entity)

    override fun updateEntity(id: String, entity: Parking): Parking {
        val updatedEntity: Parking =
            parkingMongoRepository.findById(id).orElseThrow { EntityNotFoundException("Parking with id=$id not found") }
        updatedEntity.location = entity.location
        updatedEntity.tradeNetwork = entity.tradeNetwork
        updatedEntity.countOfParkingSpots = entity.countOfParkingSpots
        return parkingMongoRepository.save(updatedEntity)
    }

    override fun deleteEntity(id: String): Unit = parkingMongoRepository.deleteById(id)
}
