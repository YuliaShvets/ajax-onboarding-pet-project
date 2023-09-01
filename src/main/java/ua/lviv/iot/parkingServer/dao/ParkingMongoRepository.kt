package ua.lviv.iot.parkingServer.dao

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ua.lviv.iot.parkingServer.model.Parking

@Repository
interface ParkingMongoRepository : MongoRepository<Parking, Long>
