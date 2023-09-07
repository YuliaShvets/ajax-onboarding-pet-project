package ua.lviv.iot.parkingServer.repository.custom

import ua.lviv.iot.parkingServer.model.Parking

interface ParkingRepositoryCustom  {

    fun findParkingByLocation(location: String) : List<Parking>

    fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int) : List<Parking>

    fun findAllByTradeNetwork(tradeNetwork : String) : List<Parking>

}
