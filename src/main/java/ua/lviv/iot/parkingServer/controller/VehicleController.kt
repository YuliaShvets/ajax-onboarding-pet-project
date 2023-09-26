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
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@RestController
@RequestMapping("/vehicle")
class VehicleController(private val vehicleService: VehicleServiceInterface) {

    @PostMapping
    fun addVehicle(@RequestBody vehicle: Vehicle): Mono<Vehicle> = vehicleService.addEntity(vehicle)

    @GetMapping
    fun getAllVehicle(): Flux<Vehicle> = vehicleService.findAllEntities()

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: String): Mono<Vehicle> = vehicleService.findEntityById(id)

    @PutMapping
    fun updateVehicle(@RequestBody vehicle: Vehicle): Mono<Vehicle> =
        vehicleService.updateEntity(vehicle)

    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: String) = vehicleService.deleteEntity(id)

    @GetMapping("/{number}")
    fun findVehicleByNumber(@PathVariable number: String): Mono<Vehicle> {
        return vehicleService.findVehicleByNumber(number)
    }
}
