package ua.lviv.iot.parkingServer.logic

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.util.concurrent.ConcurrentHashMap
import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.ParkingFileStore
import ua.lviv.iot.parkingServer.model.Parking
import java.util.concurrent.atomic.AtomicLong
import ua.lviv.iot.parkingServer.logic.exception.EntityNotFoundException

@Service
class ParkingService(private val parkingFileStore: ParkingFileStore) {
    private val parking: MutableMap<Long, Parking> = ConcurrentHashMap()
    private var id: AtomicLong = AtomicLong(1L)

    fun findAllParking(): List<Parking> = parking.values.toList()

    fun findParkingById(id: Long): Parking =
        parking[id] ?: throw EntityNotFoundException("Parking with id=$id not found")

    fun addParking(place: Parking): Parking {
        val newId = id.getAndIncrement()
        place.id = newId
        parking[newId] = place
        return place
    }

    fun updateParking(id: Long, place: Parking): Parking {
        place.id = id
        parking[id] = place
        return place
    }

    fun deleteParking(id: Long): Parking =
        parking.remove(id) ?: throw EntityNotFoundException("Parking with id=$id doesn't exist")

    @PreDestroy
    fun saveParkingData() {
        parkingFileStore.saveDataToFile(parkingFileStore, parking)
    }

    @PostConstruct
    fun parkingDataToHashmap() {
        parkingFileStore.loadDataToHashmap(parkingFileStore, parking, id)
    }
}