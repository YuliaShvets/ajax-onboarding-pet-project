package ua.lviv.iot.parkingServer.controller

import org.springframework.web.bind.annotation.*
import ua.lviv.iot.parkingServer.logic.VehicleService
import ua.lviv.iot.parkingServer.model.Vehicle

@RestController
@RequestMapping("/vehicle")
class VehicleController(private val vehicleService: VehicleService) {

    @PostMapping
    fun addVehicle(@RequestBody vehicle: Vehicle): Vehicle = vehicleService.addVehicle(vehicle)

    @GetMapping
    fun getAllVehicle(): List<Vehicle> = vehicleService.findAllVehicles()

    @GetMapping("/{vehicleId}")
    fun getVehicleById(@PathVariable vehicleId: Long): Vehicle? = vehicleService.findVehicleById(vehicleId)

    @PutMapping("/{vehicleId}")
    fun updateVehicle(@PathVariable vehicleId: Long, @RequestBody vehicle: Vehicle): Vehicle =
        vehicleService.updateVehicle(vehicleId, vehicle)

    @DeleteMapping("/{vehicleId}")
    fun deleteVehicle(@PathVariable vehicleId: Long): Vehicle? = vehicleService.deleteVehicle(vehicleId)
}
