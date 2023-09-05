package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.annotation.TrackMetrics
import ua.lviv.iot.parkingServer.repository.ParkingRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@TrackMetrics
@Service
class ParkingService(
    private val parkingRepository: ParkingRepository
) : ParkingServiceInterface {
    override fun findAllEntities(): List<Parking> = parkingRepository.findAll()

    override fun findEntityById(id: String): Parking = parkingRepository.findById(id)
//        .orElseThrow{ EntityNotFoundException("Parking with id=$id not found") }

    override fun addEntity(entity: Parking): Parking = parkingRepository.save(entity)

    override fun updateEntity(entity: Parking): Parking {
        return parkingRepository.save(entity)
    }

    override fun deleteEntity(id: String): Unit = parkingRepository.deleteById(id)
}
