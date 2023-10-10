package ua.lviv.iot.parkingServer.controller

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
import ua.lviv.iot.parkingServer.kafka.EventObserver
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.service.interfaces.ParkingSpotServiceInterface

@RestController
@RequestMapping("/parkingSpot")
class ParkingSpotController(
    private val parkingSpotService: ParkingSpotServiceInterface,
    private val eventObserver: EventObserver
) {
    @PostMapping
    fun addParkingSpot(@RequestBody parkingSpot: ParkingSpot): Mono<ParkingSpot> =
        parkingSpotService.addEntity(parkingSpot)

    @GetMapping
    fun getAllParkingSpots(): Flux<ParkingSpot> {
        eventObserver.observe()
        return parkingSpotService.findAllEntities()
    }

    @GetMapping("/{parkingSpotId}")
    fun getParkingSpotById(@PathVariable parkingSpotId: String): Mono<ParkingSpot> =
        parkingSpotService.findEntityById(parkingSpotId)

    @PutMapping
    fun updateParkingSpot(@RequestBody parkingSpot: ParkingSpot): Mono<ParkingSpot> =
        parkingSpotService.updateEntity(parkingSpot)

    @DeleteMapping("/{parkingSpotId}")
    fun deleteParkingSpot(@PathVariable parkingSpotId: String) =
        parkingSpotService.deleteEntity(parkingSpotId)

    @GetMapping("/{isAvailable}")
    fun findParkingSpotByAvailability(@PathVariable isAvailable: Boolean): Flux<ParkingSpot> {
        return parkingSpotService.findParkingSpotByAvailability(isAvailable)
    }

}
