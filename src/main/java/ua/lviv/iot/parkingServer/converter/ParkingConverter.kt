package ua.lviv.iot.parkingServer.converter

import com.example.ParkingOuterClass
import com.example.ParkingOuterClass.ParkingRequest
import com.example.ParkingOuterClass.ParkingResponse
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.Parking

@Component
class ParkingConverter {

    fun parkingToProto(parking: Parking): ParkingOuterClass.Parking {
        return ParkingOuterClass.Parking.newBuilder()
            .setLocation(parking.location)
            .setTradeNetwork(parking.tradeNetwork)
            .setCountOfParkingSpots(parking.countOfParkingSpots)
            .build()

    }

    fun protoToParking(parkingProto: ParkingOuterClass.Parking): Parking {
        return Parking(
            location = parkingProto.location,
            tradeNetwork = parkingProto.tradeNetwork,
            countOfParkingSpots = parkingProto.countOfParkingSpots
        )
    }

    fun parkingToProtoResponse(
        parking: Parking
    ): ParkingResponse {
        return ParkingResponse.newBuilder()
            .setParking(parkingToProto(parking))
            .build()
    }

    fun protoRequestToParking(parkingProto: ParkingRequest): Parking {
        return Parking(
            location = parkingProto.parking.location,
            tradeNetwork = parkingProto.parking.tradeNetwork,
            countOfParkingSpots = parkingProto.parking.countOfParkingSpots
        )
    }
}
