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
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@RestController
@RequestMapping("/parking")
class ParkingController(private val parkingService: ParkingServiceInterface) {
  
    @PostMapping
    fun addParking(@RequestBody parking: Parking): Mono<Parking> = parkingService.addEntity(parking)

    @GetMapping
    fun getAllParking(): Flux<Parking> = parkingService.findAllEntities()

    @GetMapping("/{parkingId}")
    fun getParkingById(@PathVariable parkingId: String): Mono<Parking> = parkingService.findEntityById(parkingId)

    @PutMapping
    fun updateParking(@RequestBody place: Parking): Mono<Parking> =
        parkingService.updateEntity(place)

    @DeleteMapping("/{parkingId}")
    fun deleteParking(@PathVariable parkingId: String) = parkingService.deleteEntity(parkingId)

    @GetMapping("/location/{location}")
    fun findParkingByLocation(@PathVariable location: String): Flux<Parking> =
        parkingService.findParkingByLocation(location)

    @GetMapping("/gt/{countOfParkingSpot}")
    fun findParkingByCountOfParkingSpotsGreaterThan(@PathVariable countOfParkingSpot: Int): Flux<Parking> {
        return parkingService.findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot)
    }

    @GetMapping("/trade-network/{tradeNetwork}")
    fun findAllByTradeNetwork(@PathVariable tradeNetwork: String): Flux<Parking> {
        return parkingService.findAllByTradeNetwork(tradeNetwork)
    }

}
