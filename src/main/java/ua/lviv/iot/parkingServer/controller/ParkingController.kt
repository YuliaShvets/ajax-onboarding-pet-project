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

    @PutMapping("/{parkingId}")
    fun updateParking(@PathVariable parkingId: String, @RequestBody place: Parking): Parking =
        parkingService.updateEntity(parkingId, place)

    @DeleteMapping("/{parkingId}")
    fun deleteParking(@PathVariable parkingId: String) = parkingService.deleteEntity(parkingId)

}
