package ua.lviv.iot.parkingServer.model

import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

data class ParkingSpot(
    var parkingSpotId: Long,
    var isAvailable: Boolean,
    var size: ParkingSpotSize
) : Record() {

    override fun getHeaders(): String = listOf("Parking spot id", "Is available", "Size").joinToString(separator = ", ")

    override fun toCSV(): String = "$parkingSpotId, $isAvailable, $size"

}
