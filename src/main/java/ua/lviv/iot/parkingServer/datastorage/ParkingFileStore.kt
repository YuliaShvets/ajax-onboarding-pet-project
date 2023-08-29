package ua.lviv.iot.parkingServer.datastorage

import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.Parking

@Component
class ParkingFileStore : AbstractFileStore<Parking>() {
    override fun getRecordName(): String = "parking"

    override fun convert(values: List<String>): Parking {
        return Parking(
            id = values[0].toLong(),
            location = values[1],
            tradeNetwork = values[2],
            countOfParkingSpots = values[3].toInt()
        )
    }
}
