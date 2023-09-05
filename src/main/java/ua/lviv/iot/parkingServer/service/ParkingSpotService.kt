package ua.lviv.iot.parkingServer.service

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.repository.ParkingSpotRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.service.interfaces.ParkingSpotServiceInterface

@Service
class ParkingSpotService(
    private val parkingSpotRepository: ParkingSpotRepository
) : ParkingSpotServiceInterface {
    override fun findAllEntities(): List<ParkingSpot> = parkingSpotRepository.findAll()

    override fun findEntityById(id: String): ParkingSpot = parkingSpotRepository.findById(id)
        .orElseThrow { EntityNotFoundException("Parking spot with id=$id not found") }

    override fun addEntity(entity: ParkingSpot): ParkingSpot = parkingSpotRepository.save(entity)

    override fun updateEntity(entity: ParkingSpot): ParkingSpot {
       return parkingSpotRepository.save(entity)
    }

    override fun deleteEntity(id: String): Unit = parkingSpotRepository.deleteById(id)

}
