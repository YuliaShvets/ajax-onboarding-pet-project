package ua.lviv.iot.parkingServer.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ua.lviv.iot.parkingServer.model.Parking

@Repository
interface ParkingRepository : MongoRepository<Parking, String>
