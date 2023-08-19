package ua.lviv.iot.parkingServer.datastorage

import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.Parking

@Component
class ParkingFileStore : AbstractFIleStore<Parking>() {
    override fun  getRecordName(): String = "parking"

    override fun convert(values: List<String>): Parking {
        return Parking(values[0].toLong(), values[1], values[2], values[3].toInt())
    }
}