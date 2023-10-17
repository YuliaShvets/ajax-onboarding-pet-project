package ua.lviv.iot.application.service

import com.google.protobuf.GeneratedMessageV3
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.infrastructure.converter.proto.ParkingSpotConverter
import ua.lviv.iot.application.repository.ParkingSpotRepositoryOutPort
import ua.lviv.iot.domain.ParkingSpot
import ua.lviv.iot.exception.EntityNotFoundException

@Service
class ParkingSpotService(
    private val parkingSpotRepository: ParkingSpotRepositoryOutPort,
    private val parkingSpotConverter: ParkingSpotConverter,
    private val parkingSpotKafkaProducer: ReactiveKafkaProducerTemplate<String, GeneratedMessageV3>
) : ParkingSpotInPort {

    override fun findAllEntities(): Flux<ParkingSpot> = parkingSpotRepository.findAll()

    override fun findEntityById(id: String): Mono<ParkingSpot> = parkingSpotRepository.findById(id)
        .switchIfEmpty(Mono.error(EntityNotFoundException("Parking spot with id=$id not found")))

    override fun addEntity(entity: ParkingSpot): Mono<ParkingSpot> {
        return parkingSpotRepository.save(entity)
            .flatMap { savedEntity ->
                if (savedEntity.isAvailable) {
                    return@flatMap parkingSpotKafkaProducer.send(
                        "ADDED_AVAILABLE_PARKING_SPOT",
                        parkingSpotConverter.parkingSpotToProtoResponse(savedEntity)
                    )
                        .then(Mono.just(savedEntity))
                        .doOnSuccess {
                            println("Publish event to Kafka - ADDED_AVAILABLE_PARKING_SPOT")
                        }
                } else {
                    return@flatMap Mono.just(savedEntity)
                }
            }
    }

    override fun updateEntity(entity: ParkingSpot): Mono<ParkingSpot> {
        return parkingSpotRepository.update(entity)
    }

    override fun deleteEntity(id: String): Mono<Void> = parkingSpotRepository.deleteById(id)

    override fun findParkingSpotByAvailability(isAvailable: Boolean): Flux<ParkingSpot> {
        return parkingSpotRepository.findParkingSpotByAvailability(isAvailable)
    }

}
