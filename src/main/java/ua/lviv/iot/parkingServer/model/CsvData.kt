package ua.lviv.iot.parkingServer.model

interface CsvData {
    val id: Long
    fun getHeaders(): String
    fun toCSV(): String
}
