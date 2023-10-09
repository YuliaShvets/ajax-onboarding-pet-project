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
            }

        }.build()
    }

    fun protoToParkingSpot(parkingSpotProto: ParkingSpotOuterClass.ParkingSpot): ParkingSpot {
        return ParkingSpot(
            isAvailable = parkingSpotProto.isAvailable,
            size = when (parkingSpotProto.size) {
                ParkingSpotOuterClass.ParkingSpotSize.LARGE -> ParkingSpotSize.LARGE
                ParkingSpotOuterClass.ParkingSpotSize.COMPACT -> ParkingSpotSize.COMPACT
                ParkingSpotOuterClass.ParkingSpotSize.MOTORBIKE -> ParkingSpotSize.MOTORBIKE
                else -> ParkingSpotSize.COMPACT
            }
        )
    }

    fun parkingSpotToProtoResponse(
        parkingSpot: ParkingSpot
    ): ParkingSpotOuterClass.ParkingSpotResponse {
        return ParkingSpotOuterClass.ParkingSpotResponse.newBuilder()
            .setParkingSpot(parkingSpotToProto(parkingSpot))
            .build()
    }

    fun protoRequestToParkingSpot(parkingSpotProto: ParkingSpotOuterClass.ParkingSpotRequest): ParkingSpot {
        return ParkingSpot(
            isAvailable = parkingSpotProto.parkingSpot.isAvailable,
            size = when (parkingSpotProto.parkingSpot.size) {
                ParkingSpotOuterClass.ParkingSpotSize.LARGE -> ParkingSpotSize.LARGE
                ParkingSpotOuterClass.ParkingSpotSize.COMPACT -> ParkingSpotSize.COMPACT
                ParkingSpotOuterClass.ParkingSpotSize.MOTORBIKE -> ParkingSpotSize.MOTORBIKE
                else -> ParkingSpotSize.COMPACT
            }
        )
    }
}
