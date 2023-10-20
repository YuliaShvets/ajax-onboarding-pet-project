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
import ua.lviv.iot.application.service.ParkingInPort
import ua.lviv.iot.domain.Parking

@RestController
@RequestMapping("/parking")
class ParkingController(
    private val parkingInPort: ParkingInPort
) {

    @PostMapping
    fun addParking(@RequestBody parking: Parking): Mono<Parking> = parkingInPort.addEntity(parking)

    @GetMapping
    fun getAllParking(): Flux<Parking> = parkingInPort.findAllEntities()

    @GetMapping("/{parkingId}")
    fun getParkingById(@PathVariable parkingId: String): Mono<Parking> = parkingInPort.findEntityById(parkingId)

    @PutMapping
    fun updateParking(@RequestBody place: Parking): Mono<Parking> =
        parkingInPort.updateEntity(place)

    @DeleteMapping("/{parkingId}")
    fun deleteParking(@PathVariable parkingId: String) = parkingInPort.deleteEntity(parkingId)

    @GetMapping("/location/{location}")
    fun findParkingByLocation(@PathVariable location: String): Flux<Parking> =
        parkingInPort.findParkingByLocation(location)

    @GetMapping("/gt/{countOfParkingSpot}")
    fun findParkingByCountOfParkingSpotsGreaterThan(@PathVariable countOfParkingSpot: Int): Flux<Parking> {
        return parkingInPort.findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot)
    }

    @GetMapping("/trade-network/{tradeNetwork}")
    fun findAllByTradeNetwork(@PathVariable tradeNetwork: String): Flux<Parking> {
        return parkingInPort.findAllByTradeNetwork(tradeNetwork)
    }

}
