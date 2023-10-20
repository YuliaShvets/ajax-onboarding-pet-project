package ua.lviv.iot.application.service

import ua.lviv.iot.application.repository.ParkingRepositoryOutPort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.config.bpp.annotation.TrackMetrics
import ua.lviv.iot.domain.Parking
import ua.lviv.iot.exception.EntityNotFoundException

@Service
@TrackMetrics
class ParkingService(
    private val parkingRepository: ParkingRepositoryOutPort,
) : ParkingInPort {

    override fun findAllEntities(): Flux<Parking> = parkingRepository.findAll()

    override fun findEntityById(id: String): Mono<Parking> {
        return parkingRepository.findById(id)
            .switchIfEmpty(Mono.error(EntityNotFoundException("Parking spot with id=$id not found")))

    }

    override fun addEntity(entity: Parking): Mono<Parking> = parkingRepository.save(entity)

    override fun updateEntity(entity: Parking): Mono<Parking> {
        return parkingRepository.update(entity)
    }

    override fun deleteEntity(id: String): Mono<Unit> = parkingRepository.deleteById(id)

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
