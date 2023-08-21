package ua.lviv.iot.parkingServer.logic

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.ParkingFileStore
import ua.lviv.iot.parkingServer.model.Parking
import java.util.concurrent.atomic.AtomicLong
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class ParkingService(private val parkingFileStore: ParkingFileStore) {
    private val parking: MutableMap<Long, Parking> = HashMap()
    private var id: AtomicLong = AtomicLong(1L)

    fun findAllParking(): List<Parking> = ArrayList<Parking>(parking.values)

    fun findParkingById(id: Long): Parking? = parking[id]

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

    fun deleteParking(id: Long): Parking? = parking.remove(id)

    @PreDestroy
    fun saveParkingData() {
        parkingFileStore.saveDataToFile(parkingFileStore, parking)
    }

    @PostConstruct
    fun parkingDataToHashmap() {
        parkingFileStore.loadDataToHashmap(parkingFileStore, parking, id)
    }
}