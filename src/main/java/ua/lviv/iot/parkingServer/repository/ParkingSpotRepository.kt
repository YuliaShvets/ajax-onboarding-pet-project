package ua.lviv.iot.parkingServer.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ua.lviv.iot.parkingServer.model.ParkingSpot

@Repository
interface ParkingSpotRepository : MongoRepository<ParkingSpot, String>
