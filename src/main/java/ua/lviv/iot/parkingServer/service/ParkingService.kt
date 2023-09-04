package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.annotation.TrackMetrics
import ua.lviv.iot.parkingServer.repository.ParkingMongoRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@TrackMetrics
@Service
class ParkingService(private val parkingMongoRepository: ParkingMongoRepository) : ParkingServiceInterface {
    override fun findAllEntities(): List<Parking> = parkingMongoRepository.findAll()

    override fun findEntityById(id: Long): Parking = parkingMongoRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Parking with id=$id not found") }

    override fun addEntity(entity: Parking): Parking = parkingMongoRepository.save(entity)

    override fun updateEntity(id: Long, entity: Parking): Parking {
        entity.id = findEntityById(id).id
        addEntity(entity)
        return addEntity(entity)
    }

    override fun deleteEntity(id: Long) = parkingMongoRepository.deleteById(id)

}
