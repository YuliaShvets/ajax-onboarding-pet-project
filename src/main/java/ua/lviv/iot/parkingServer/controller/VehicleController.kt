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

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Long): Vehicle = vehicleService.findVehicleById(id)

    @PutMapping("/{id}")
    fun updateVehicle(@PathVariable id: Long, @RequestBody vehicle: Vehicle): Vehicle =
        vehicleService.updateVehicle(id, vehicle)

    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: Long): Vehicle = vehicleService.deleteVehicle(id)
}
