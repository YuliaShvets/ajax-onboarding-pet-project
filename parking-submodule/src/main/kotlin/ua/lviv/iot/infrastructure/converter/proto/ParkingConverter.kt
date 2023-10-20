package ua.lviv.iot.infrastructure.converter.proto

import ua.lviv.iot.domain.Parking

import org.springframework.stereotype.Component
import ua.lviv.iot.ParkingOuterClass

@Component
class ParkingConverter {

    fun parkingToProto(parking: Parking): ParkingOuterClass.Parking {
        return ParkingOuterClass.Parking.newBuilder().apply {
            location = parking.location
            tradeNetwork = parking.tradeNetwork
            countOfParkingSpots = parking.countOfParkingSpots

        }.build()
    }

    fun protoToParking(parkingProto: ParkingOuterClass.Parking): Parking {
        return Parking(
            id = null,
            location = parkingProto.location,
            tradeNetwork = parkingProto.tradeNetwork,
            countOfParkingSpots = parkingProto.countOfParkingSpots
        )
    }

    fun parkingToProtoResponse(
        parking: Parking
    ): ParkingOuterClass.CreateParkingResponse {
        return ParkingOuterClass.CreateParkingResponse.newBuilder()
            .setParking(parkingToProto(parking))
            .build()
    }

    fun protoRequestToParking(parkingProto: ParkingOuterClass.CreateParkingRequest): Parking {
        return Parking(
            id = null,
            location = parkingProto.parking.location,
            tradeNetwork = parkingProto.parking.tradeNetwork,
            countOfParkingSpots = parkingProto.parking.countOfParkingSpots
        )
    }
}
