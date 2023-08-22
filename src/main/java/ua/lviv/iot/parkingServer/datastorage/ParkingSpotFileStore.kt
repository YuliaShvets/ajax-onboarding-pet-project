package ua.lviv.iot.parkingServer.datastorage

import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

@Component
class ParkingSpotFileStore : AbstractFileStore<ParkingSpot>() {
    override fun getRecordName(): String = "parking-spot"

    override fun convert(values: List<String>): ParkingSpot {
        return ParkingSpot(
            id = values[0].toLong(),
            isAvailable = values[1].toBoolean(),
            size = ParkingSpotSize.valueOf(values[2])
        )
    }
}