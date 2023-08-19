package ua.lviv.iot.parkingServer.logic

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.VehicleFileStore
import ua.lviv.iot.parkingServer.model.Vehicle
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class VehicleService(private val vehicleFileStore: VehicleFileStore) {
    private val vehicles: MutableMap<Long, Vehicle> = HashMap()
    private var index: Long = 0L

    fun findAllVehicles(): List<Vehicle> = ArrayList<Vehicle>(vehicles.values)

    fun findVehicleById(id: Long): Vehicle? = vehicles[id]

    fun addVehicle(vehicle: Vehicle): Vehicle {
        index += 1
        vehicle.vehicleId = index
        vehicles[index] = vehicle
        return vehicle
    }

    fun updateVehicle(id: Long, vehicle: Vehicle): Vehicle {
        vehicle.vehicleId = id
        vehicles[id] = vehicle
        return vehicle
    }

    fun deleteVehicle(id: Long): Vehicle? = vehicles.remove(id)

    @PreDestroy
    fun saveVehicleData() {
        val vehicleList: List<Vehicle> = vehicles.values.stream().toList()
        vehicleFileStore.saveRecords(vehicleList)
    }

    @PostConstruct
    fun vehicleDataToHashmap() {
        val vehicleList: List<Vehicle> = vehicleFileStore.readRecords()
        for (vehicle: Vehicle in vehicleList) {
            index += 1
            if (vehicle.vehicleId > index) {
                index = vehicle.vehicleId
            }
            vehicles[vehicle.vehicleId] = vehicle
        }
    }
}