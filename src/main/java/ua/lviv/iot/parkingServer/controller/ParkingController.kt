package ua.lviv.iot.parkingServer.controller

import org.springframework.web.bind.annotation.*
import ua.lviv.iot.parkingServer.logic.ParkingService
import ua.lviv.iot.parkingServer.model.Parking

@RestController
@RequestMapping("/parking")
class ParkingController(private val parkingService: ParkingService) {
    @PostMapping
    fun addParking(@RequestBody parking: Parking): Parking = parkingService.addParking(parking)

    @GetMapping
    fun getAllParking(): List<Parking> = parkingService.findAllParking()

    @GetMapping("/{parkingId}")
    fun getParkingById(@PathVariable parkingId: Long): Parking? = parkingService.findParkingById(parkingId)

    @PutMapping("/{parkingId}")
    fun updateParking(@PathVariable parkingId: Long, @RequestBody place: Parking): Parking =
        parkingService.updateParking(parkingId, place)

    @DeleteMapping("/{parkingId}")
    fun deleteParking(@PathVariable parkingId: Long): Parking? = parkingService.deleteParking(parkingId)

}
