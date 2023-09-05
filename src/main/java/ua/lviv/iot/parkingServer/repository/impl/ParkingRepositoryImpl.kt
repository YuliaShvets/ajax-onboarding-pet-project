package ua.lviv.iot.parkingServer.repository.impl


import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.repository.ParkingRepository

@Repository
class ParkingRepositoryImpl(private val mongoTemplate: MongoTemplate) : ParkingRepository {
    override fun findAll() : List<Parking> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Parking {
        TODO("Not yet implemented")
    }

    override fun save(entity: Parking): Parking {
        TODO("Not yet implemented")
    }

    override fun update(entity: Parking): Parking {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: String) {
        TODO("Not yet implemented")
    }

    override fun findParkingByLocation(location: String) {
        TODO("Not yet implemented")
    }

}
