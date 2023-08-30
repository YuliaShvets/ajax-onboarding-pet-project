package ua.lviv.iot.parkingServer.model


data class Parking(
    override var id: Long,
    var location: String,
    var tradeNetwork: String,
    var countOfParkingSpots: Int
) : CsvData {

    override fun getHeaders(): String =
        listOf("Id", "Location", "Trade Network", "Count of parking spots").joinToString(separator = ", ")

    override fun toCSV(): String = "$id, $location, $tradeNetwork, $countOfParkingSpots"
}
