package ua.lviv.iot.parkingServer.converter

import org.springframework.stereotype.Component
import ua.lviv.iot.ParkingSpotOuterClass
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

@Component
class ParkingSpotConverter {

    fun parkingSpotToProto(parkingSpot: ParkingSpot): ParkingSpotOuterClass.ParkingSpot {
        return ParkingSpotOuterClass.ParkingSpot.newBuilder().apply {
            isAvailable = parkingSpot.isAvailable
            size = when (parkingSpot.size) {
                ParkingSpotSize.LARGE -> ParkingSpotOuterClass.ParkingSpotSize.LARGE
                ParkingSpotSize.COMPACT -> ParkingSpotOuterClass.ParkingSpotSize.COMPACT
                ParkingSpotSize.MOTORBIKE -> ParkingSpotOuterClass.ParkingSpotSize.MOTORBIKE
                else -> ParkingSpotOuterClass.ParkingSpotSize.DEFAULT
            }

        }.build()
    }

    fun parkingSpotToProtoResponse(
        parkingSpot: ParkingSpot
    ): ParkingSpotOuterClass.CreateParkingSpotResponse {
        return ParkingSpotOuterClass.CreateParkingSpotResponse.newBuilder()
            .setParkingSpot(parkingSpotToProto(parkingSpot))
            .build()
    }
}
