package ua.lviv.iot.parkingServer.logic

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.ParkingFileStore
import ua.lviv.iot.parkingServer.model.Parking
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class ParkingService(private val parkingFileStore: ParkingFileStore) {
    private val parking: MutableMap<Long, Parking> = HashMap()
    private var index: Long = 0L

    fun findAllParking(): List<Parking> = ArrayList<Parking>(parking.values)

    fun findParkingById(id: Long): Parking? = parking[id]

    fun addParking(place: Parking): Parking {
        index += 1
        place.parkingId = index
        parking[index] = place
        return place
    }

    fun updateParking(id: Long, place: Parking): Parking {
        place.parkingId = id
        parking[id] = place
        return place
    }

    fun deleteParking(id: Long): Parking? = parking.remove(id)

    @PreDestroy
    fun saveParkingData() {
        val parkingList: List<Parking> = parking.values.stream().toList()
        parkingFileStore.saveRecords(parkingList)
    }

    @PostConstruct
    fun parkingDataToHashmap() {
        val parkingList: List<Parking> = parkingFileStore.readRecords()
        for (parkingPlace: Parking in parkingList) {
            index += 1
            if (parkingPlace.parkingId > index) {
                index = parkingPlace.parkingId
            }
            parking[parkingPlace.parkingId] = parkingPlace
        }
    }
}