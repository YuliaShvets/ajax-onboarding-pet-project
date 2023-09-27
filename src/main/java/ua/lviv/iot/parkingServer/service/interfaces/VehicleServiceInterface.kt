package ua.lviv.iot.parkingServer.service.interfaces

import reactor.core.publisher.Mono
import ua.lviv.iot.parkingServer.model.Vehicle

interface VehicleServiceInterface : GeneralServiceInterface<Vehicle, String> {

    fun findVehicleByNumber(number: String) : Mono<Vehicle>
}
