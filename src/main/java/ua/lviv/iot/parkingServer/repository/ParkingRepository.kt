package ua.lviv.iot.parkingServer.repository

import ua.lviv.iot.parkingServer.model.Parking

interface ParkingRepository  {
    fun findAll(): List<Parking>
    fun findById(id: String): Parking
    fun save(entity: Parking): Parking

    fun update(entity: Parking): Parking

    fun deleteById(id: String)

    fun findParkingByLocation(location : String)
}
