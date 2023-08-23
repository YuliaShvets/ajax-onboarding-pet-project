package ua.lviv.iot.parkingServer.logic

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.ParkingSpotFileStore
import ua.lviv.iot.parkingServer.model.ParkingSpot
import java.util.concurrent.atomic.AtomicLong
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import ua.lviv.iot.parkingServer.logic.exception.EntityNotFoundException

@Service
class ParkingSpotService(private val parkingSpotFileStore: ParkingSpotFileStore) {
    private val parkingSpots: MutableMap<Long, ParkingSpot> = HashMap()
    private var id: AtomicLong = AtomicLong(1L)

    fun findAllParkingSpots(): List<ParkingSpot> = parkingSpots.values.toList()

    fun findParkingSpotById(id: Long): ParkingSpot =
        parkingSpots[id] ?: throw EntityNotFoundException("Parking spot with id=$id not found")

    fun addParkingSpot(parkingSpot: ParkingSpot): ParkingSpot {
        val newId = id.getAndIncrement()
        parkingSpot.id = newId
        parkingSpots[newId] = parkingSpot
        return parkingSpot
    }

    fun updateParkingSpot(id: Long, parkingSpot: ParkingSpot): ParkingSpot {
        parkingSpot.id = id
        parkingSpots[id] = parkingSpot
        return parkingSpot
    }

    fun deleteParkingSpot(id: Long): ParkingSpot =
        parkingSpots.remove(id) ?: throw EntityNotFoundException("Parking spot with id=$id doesn't exist")

    @PreDestroy
    fun saveParkingSpotData() {
        parkingSpotFileStore.saveDataToFile(parkingSpotFileStore, parkingSpots)
    }

    @PostConstruct
    fun parkingSpotDataToHashmap() {
        parkingSpotFileStore.loadDataToHashmap(parkingSpotFileStore, parkingSpots, id)
    }
}