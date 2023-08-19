package ua.lviv.iot.parkingServer.model

abstract class Record {
    abstract fun getHeaders(): String
    abstract fun toCSV(): String
}
