package ua.lviv.iot.parkingServer.logic

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.ParkingSpotFileStore
import ua.lviv.iot.parkingServer.model.ParkingSpot
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class ParkingSpotService(private val parkingSpotFileStore: ParkingSpotFileStore) {
    private val parkingSpots: MutableMap<Long, ParkingSpot> = HashMap()
    private var index: Long = 0L

    fun findAllParkingSpots(): List<ParkingSpot> = ArrayList<ParkingSpot>(parkingSpots.values)

    fun findParkingSpotById(id: Long): ParkingSpot? = parkingSpots[id]

    fun addParkingSpot(parkingSpot: ParkingSpot): ParkingSpot {
        index += 1
        parkingSpot.parkingSpotId = index
        parkingSpots[index] = parkingSpot
        return parkingSpot
    }

    fun updateParkingSpot(id: Long, parkingSpot: ParkingSpot): ParkingSpot {
        parkingSpot.parkingSpotId = id
        parkingSpots[id] = parkingSpot
        return parkingSpot
    }

    fun deleteParkingSpot(id: Long): ParkingSpot? = parkingSpots.remove(id)

    @PreDestroy
    fun saveParkingSpotData() {
        val parkingSpotList: List<ParkingSpot> = parkingSpots.values.stream().toList()
        parkingSpotFileStore.saveRecords(parkingSpotList)
    }

    @PostConstruct
    fun parkingSpotDataToHashmap() {
        val parkingSpotList: List<ParkingSpot> = parkingSpotFileStore.readRecords()
        for (parkingSpot: ParkingSpot in parkingSpotList) {
            index += 1
            if (parkingSpot.parkingSpotId > index) {
                index = parkingSpot.parkingSpotId
            }
            parkingSpots[parkingSpot.parkingSpotId] = parkingSpot
        }
    }
}