package ua.lviv.iot.parkingServer.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.repository.custom.ParkingRepositoryCustom

@Repository
interface ParkingRepository : MongoRepository<Parking, String>, ParkingRepositoryCustom {

}
