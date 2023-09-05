package ua.lviv.iot.parkingServer.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ua.lviv.iot.parkingServer.model.Vehicle

@Repository
interface VehicleMongoRepository : MongoRepository<Vehicle, String>
