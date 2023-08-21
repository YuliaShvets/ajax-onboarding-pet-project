package ua.lviv.iot.parkingServer.model

import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize

data class ParkingSpot(
    override var id: Long,
    var isAvailable: Boolean,
    var size: ParkingSpotSize
) : CsvData {

    override fun getHeaders(): String = listOf("Id", "Is available", "Size").joinToString(separator = ", ")

    override fun toCSV(): String = "$id, $isAvailable, $size"

}