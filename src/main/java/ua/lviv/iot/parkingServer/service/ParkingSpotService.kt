package ua.lviv.iot.parkingServer.service

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.service.interfaces.ParkingSpotServiceInterface

@Service
class ParkingSpotService(private val parkingSpotFileStore: ParkingSpotFileStore) : ParkingSpotServiceInterface {
    private val parkingSpots: MutableMap<Long, ParkingSpot> = ConcurrentHashMap()
    private var id: AtomicLong = AtomicLong(1L)

    override fun findAllEntities(): List<ParkingSpot> = parkingSpots.values.toList()

    override fun findEntityById(id: Long): ParkingSpot =
        parkingSpots[id] ?: throw EntityNotFoundException("Parking spot with id=$id not found")


    override fun addEntity(entity: ParkingSpot): ParkingSpot {
        val newId = id.getAndIncrement()
        entity.id = newId
        parkingSpots[newId] = entity
        return entity
    }

    override fun updateEntity(id: Long, entity: ParkingSpot): ParkingSpot {
        entity.id = id
        parkingSpots[id] = entity
        return entity
    }

    override fun deleteEntity(id: Long): ParkingSpot =
        parkingSpots.remove(id) ?: throw EntityNotFoundException("Parking spot with id=$id doesn't exist")

    @PostConstruct
    override fun entityDataToHashMap() {
        parkingSpotFileStore.loadDataToHashmap(parkingSpotFileStore, parkingSpots, id)
    }

    @PreDestroy
    override fun saveEntityData() {
        parkingSpotFileStore.saveDataToFile(parkingSpotFileStore, parkingSpots)
    }

}
