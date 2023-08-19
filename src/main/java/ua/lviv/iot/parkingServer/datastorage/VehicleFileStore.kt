package ua.lviv.iot.parkingServer.datastorage

import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType

@Component
class VehicleFileStore : AbstractFIleStore<Vehicle>() {
    override fun getRecordName(): String = "vehicle"

    override fun convert(values: List<String>): Vehicle {
        return Vehicle(
            values[0].toLong(),
            values[1],
            VehicleType.valueOf(values[2]),
            values[3].toDouble(),
            values[4].toBoolean()
        )
    }
}
