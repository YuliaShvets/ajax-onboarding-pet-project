package ua.lviv.iot.parkingServer.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@RestController
@RequestMapping("/vehicle")
class VehicleController(private val vehicleService: VehicleServiceInterface) {

    @PostMapping
    fun addVehicle(@RequestBody vehicle: Vehicle): Vehicle = vehicleService.addEntity(vehicle)

    @GetMapping
    fun getAllVehicle(): List<Vehicle> = vehicleService.findAllEntities()

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: String): Vehicle = vehicleService.findEntityById(id)

    @PutMapping
    fun updateVehicle(@RequestBody vehicle: Vehicle): Vehicle =
        vehicleService.updateEntity(vehicle)

    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: String) = vehicleService.deleteEntity(id)
}
