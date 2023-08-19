package ua.lviv.iot.parkingServer.model

data class Parking(var parkingId: Long, var location: String, var tradeNetwork: String, var countOfParkingSpots: Int) :
    Record() {

    override fun getHeaders(): String =
        listOf("Parking id", "Location", "Trade Network", "Count of parking spots").joinToString(separator = ", ")

    override fun toCSV(): String = "$parkingId, $location, $tradeNetwork, $countOfParkingSpots"

}
