package ua.lviv.iot.parkingServer.controller

import org.springframework.web.bind.annotation.*
import ua.lviv.iot.parkingServer.logic.ParkingSpotService
import ua.lviv.iot.parkingServer.model.ParkingSpot

@RestController
@RequestMapping("/parkingSpot")
class ParkingSpotController(private val parkingSpotService: ParkingSpotService) {
    @PostMapping
    fun addParkingSpot(@RequestBody parkingSpot: ParkingSpot): ParkingSpot =
        parkingSpotService.addParkingSpot(parkingSpot)

    @GetMapping
    fun getAllParking(): List<ParkingSpot> = parkingSpotService.findAllParkingSpots()

    @GetMapping("/{parkingSpotId}")
    fun getParkingSpotById(@PathVariable parkingSpotId: Long): ParkingSpot? =
        parkingSpotService.findParkingSpotById(parkingSpotId)

    @PutMapping("/{parkingSpotId}")
    fun updateParkingSpot(@PathVariable parkingSpotId: Long, @RequestBody parkingSpot: ParkingSpot): ParkingSpot =
        parkingSpotService.updateParkingSpot(parkingSpotId, parkingSpot)

    @DeleteMapping("/{parkingSpotId}")
    fun deleteParkingSpot(@PathVariable parkingSpotId: Long): ParkingSpot? =
        parkingSpotService.deleteParkingSpot(parkingSpotId)

}
