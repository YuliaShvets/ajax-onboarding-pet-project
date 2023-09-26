package ua.lviv.iot.parkingServer.repository

import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.model.Vehicle

interface VehicleRepository : GeneralRepository<Vehicle> {

    fun findVehicleByNumber(number: String) : Mono<Vehicle>
}
