package ua.lviv.iot.parkingServer.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.service.interfaces.ParkingSpotServiceInterface

@RestController
@RequestMapping("/parkingSpot")
class ParkingSpotController(private val parkingSpotService: ParkingSpotServiceInterface) {
    @PostMapping
    fun addParkingSpot(@RequestBody parkingSpot: ParkingSpot): ParkingSpot =
        parkingSpotService.addEntity(parkingSpot)

    @GetMapping
    fun getAllParking(): List<ParkingSpot> = parkingSpotService.findAllEntities()

    @GetMapping("/{parkingSpotId}")
    fun getParkingSpotById(@PathVariable parkingSpotId: Long): ParkingSpot =
        parkingSpotService.findEntityById(parkingSpotId)

    @PutMapping("/{parkingSpotId}")
    fun updateParkingSpot(@PathVariable parkingSpotId: Long, @RequestBody parkingSpot: ParkingSpot): ParkingSpot =
        parkingSpotService.updateEntity(parkingSpotId, parkingSpot)

    @DeleteMapping("/{parkingSpotId}")
    fun deleteParkingSpot(@PathVariable parkingSpotId: Long) =
        parkingSpotService.deleteEntity(parkingSpotId)

}
