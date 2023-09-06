package ua.lviv.iot.parkingServer.service.interfaces

import ua.lviv.iot.parkingServer.model.Parking

interface ParkingServiceInterface : GeneralServiceInterface<Parking, String> {
    fun findParkingByLocation(location: String): List<Parking>

    fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int) : List<Parking>

    fun updateTradeNetworkUsingFindAndModify(oldTradeNetwork : String, newTradeNetwork : String) : Parking?
}
