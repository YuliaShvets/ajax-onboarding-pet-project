package ua.lviv.iot.domain

data class Parking(
    var id: String?,
    val location: String,
    val tradeNetwork: String,
    val countOfParkingSpots: Int
)
