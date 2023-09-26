package ua.lviv.iot.parkingServer.service

import com.mongodb.client.result.DeleteResult
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.repository.ParkingSpotRepository
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.service.interfaces.ParkingSpotServiceInterface

@Service
class ParkingSpotService(
    private val parkingSpotRepository: ParkingSpotRepository
) : ParkingSpotServiceInterface {

    override fun findAllEntities(): Flux<ParkingSpot> = parkingSpotRepository.findAll()

    override fun findEntityById(id: String): Mono<ParkingSpot> = parkingSpotRepository.findById(id)
        .switchIfEmpty(Mono.error(EntityNotFoundException("Parking spot with id=$id not found")))

    override fun addEntity(entity: ParkingSpot): Mono<ParkingSpot> = parkingSpotRepository.save(entity)

    override fun updateEntity(entity: ParkingSpot): Mono<ParkingSpot> {
        return parkingSpotRepository.update(entity)
    }

    override fun deleteEntity(id: String): Mono<DeleteResult> = parkingSpotRepository.deleteById(id)

    override fun findParkingSpotByAvailability(isAvailable: Boolean): Flux<ParkingSpot> {
        return parkingSpotRepository.findParkingSpotByAvailability(isAvailable)
    }

}
