package ua.lviv.iot.parkingServer.datastorage

import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

@Component
class ParkingSpotFileStore : AbstractFIleStore<ParkingSpot>() {
    override fun getRecordName(): String = "parkingSpot"

    override fun convert(values: List<String>): ParkingSpot {
        return ParkingSpot(values[0].toLong(), values[1].toBoolean(), ParkingSpotSize.valueOf(values[2]))
    }
}
