package ua.lviv.iot.parkingServer.repository.impl


import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.repository.custom.ParkingRepositoryCustom

class ParkingRepositoryImpl(private val mongoTemplate: MongoTemplate) : ParkingRepositoryCustom {
    override fun findParkingByLocation(location: String): List<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("location").`is`(location))
        return mongoTemplate.find(query, Parking::class.java)
    }

    override fun findParkingByCountOfParkingSpotsGreaterThan(countOfParkingSpot: Int): List<Parking> {
        val query = Query()
            .addCriteria(Criteria.where("countOfParkingSpot").gt(countOfParkingSpot))
        return mongoTemplate.find(query, Parking::class.java)
    }

    override fun updateTradeNetworkUsingFindAndModify(oldTradeNetwork: String, newTradeNetwork: String): Parking? {
        val query = Query()
            .addCriteria(Criteria.where("tradeNetwork").`is`(oldTradeNetwork))
        val updateDefinition = Update().set("tradeNetwork", newTradeNetwork)
        val findAndModifyOptions = FindAndModifyOptions().returnNew(true).upsert(true)
        return mongoTemplate.findAndModify(query, updateDefinition, findAndModifyOptions, Parking::class.java)
    }


}
