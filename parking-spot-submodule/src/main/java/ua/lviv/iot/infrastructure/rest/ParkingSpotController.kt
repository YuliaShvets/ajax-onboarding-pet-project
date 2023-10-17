package ua.lviv.iot.infrastructure.rest

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.application.service.ParkingSpotInPort
import ua.lviv.iot.domain.ParkingSpot
import ua.lviv.iot.infrastructure.grpc.observer.EventObserver

@RestController
@RequestMapping("/parkingSpot")
class ParkingSpotController(
    private val parkingSpotInPort: ParkingSpotInPort,
    private val eventObserver: EventObserver
) {
    @PostMapping
    fun addParkingSpot(@RequestBody parkingSpot: ParkingSpot): Mono<ParkingSpot> =
        parkingSpotInPort.addEntity(parkingSpot)

    @GetMapping
    fun getAllParkingSpots(): Flux<ParkingSpot> {
        eventObserver.observe()
        return parkingSpotInPort.findAllEntities()
    }

    @GetMapping("/{parkingSpotId}")
    fun getParkingSpotById(@PathVariable parkingSpotId: String): Mono<ParkingSpot> =
        parkingSpotInPort.findEntityById(parkingSpotId)

    @PutMapping
    fun updateParkingSpot(@RequestBody parkingSpot: ParkingSpot): Mono<ParkingSpot> =
        parkingSpotInPort.updateEntity(parkingSpot)

    @DeleteMapping("/{parkingSpotId}")
    fun deleteParkingSpot(@PathVariable parkingSpotId: String) =
        parkingSpotInPort.deleteEntity(parkingSpotId)

    @GetMapping("/{isAvailable}")
    fun findParkingSpotByAvailability(@PathVariable isAvailable: Boolean): Flux<ParkingSpot> {
        return parkingSpotInPort.findParkingSpotByAvailability(isAvailable)
    }

}
