package ua.lviv.iot.parkingServer.service

import com.mongodb.client.result.DeleteResult
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.annotation.TrackMetrics
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.repository.ParkingRepository
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@TrackMetrics
@Service
class ParkingService(
    private val parkingRepository: ParkingRepository
) : ParkingServiceInterface {

    override fun findAllEntities(): Flux<Parking> = parkingRepository.findAll()

    override fun findEntityById(id: String): Mono<Parking> = parkingRepository.findById(id)
        .switchIfEmpty(Mono.error(EntityNotFoundException("Parking with id=$id not found")))

    override fun addEntity(entity: Parking): Mono<Parking> = parkingRepository.save(entity)

    override fun updateEntity(entity: Parking): Mono<Parking> {
        return parkingRepository.update(entity)
    }

    override fun deleteEntity(id: String): Mono<DeleteResult> = parkingRepository.deleteById(id)

    override fun findParkingByLocation(location: String): Flux<Parking> {
        return parkingRepository.findParkingByLocation(location)
    }

    override fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int): Flux<Parking> {
        return parkingRepository.findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot)
    }

    override fun findAllByTradeNetwork(tradeNetwork: String): Flux<Parking> {
        return parkingRepository.findAllByTradeNetwork(tradeNetwork)
    }

}
