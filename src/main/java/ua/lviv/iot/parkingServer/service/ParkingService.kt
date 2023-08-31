package ua.lviv.iot.parkingServer.service

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.annotation.TrackMetrics
import ua.lviv.iot.parkingServer.datastorage.ParkingFileStore
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@TrackMetrics
@Service
class ParkingService(private val parkingFileStore: ParkingFileStore) : ParkingServiceInterface {
    private val parking: MutableMap<Long, Parking> = ConcurrentHashMap()
    private var id: AtomicLong = AtomicLong(1L)

    override fun findAllEntities(): List<Parking> = parking.values.toList()

    override fun findEntityById(id: Long): Parking =
        parking[id] ?: throw EntityNotFoundException("Parking with id=$id not found")

    override fun addEntity(entity: Parking): Parking {
        val newId = id.getAndIncrement()
        entity.id = newId
        parking[newId] = entity
        return entity
    }

    override fun updateEntity(id: Long, entity: Parking): Parking {
        entity.id = id
        parking[id] = entity
        return entity
    }

    override fun deleteEntity(id: Long): Parking =
        parking.remove(id) ?: throw EntityNotFoundException("Parking with id=$id doesn't exist")

    @PostConstruct
    override fun entityDataToHashMap() {
        parkingFileStore.loadDataToHashmap(parkingFileStore, parking, id)
    }

    @PreDestroy
    override fun saveEntityData() {
        parkingFileStore.saveDataToFile(parkingFileStore, parking)
    }

}
