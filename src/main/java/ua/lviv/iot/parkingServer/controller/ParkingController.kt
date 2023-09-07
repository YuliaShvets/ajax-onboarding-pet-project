package ua.lviv.iot.parkingServer.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@RestController
@RequestMapping("/parking")
class ParkingController(private val parkingService: ParkingServiceInterface) {
  
    @PostMapping
    fun addParking(@RequestBody parking: Parking): Parking = parkingService.addEntity(parking)

    @GetMapping
    fun getAllParking(): List<Parking> = parkingService.findAllEntities()

    @GetMapping("/{parkingId}")
    fun getParkingById(@PathVariable parkingId: String): Parking = parkingService.findEntityById(parkingId)

    @PutMapping
    fun updateParking(@RequestBody place: Parking): Parking =
        parkingService.updateEntity(place)

    @DeleteMapping("/{parkingId}")
    fun deleteParking(@PathVariable parkingId: String) = parkingService.deleteEntity(parkingId)

    @GetMapping("/location/{location}")
    fun findParkingByLocation(@PathVariable location: String): List<Parking> =
        parkingService.findParkingByLocation(location)

    @GetMapping("/gt/{countOfParkingSpot}")
    fun findParkingByCountOfParkingSpotsGreaterThan(@PathVariable countOfParkingSpot: Int): List<Parking> {
        return parkingService.findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot)
    }

    @GetMapping("/trade-network/{tradeNetwork}")
    fun findAllByTradeNetwork(@PathVariable tradeNetwork: String): List<Parking> {
        return parkingService.findAllByTradeNetwork(tradeNetwork)
    }

}
