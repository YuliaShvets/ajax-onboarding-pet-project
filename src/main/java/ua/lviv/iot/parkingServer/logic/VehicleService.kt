package ua.lviv.iot.parkingServer.logic

import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.VehicleFileStore
import ua.lviv.iot.parkingServer.model.Vehicle
import java.util.concurrent.atomic.AtomicLong
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class VehicleService(private val vehicleFileStore: VehicleFileStore) {
    private val vehicles: MutableMap<Long, Vehicle> = HashMap()
    private var id: AtomicLong = AtomicLong(1L)

    fun findAllVehicles(): List<Vehicle> = ArrayList<Vehicle>(vehicles.values)

    fun findVehicleById(id: Long): Vehicle? = vehicles[id]

    fun addVehicle(vehicle: Vehicle): Vehicle {
        val newId = id.getAndIncrement()
        vehicle.id = newId
        vehicles[newId] = vehicle
        return vehicle
    }

    fun updateVehicle(id: Long, vehicle: Vehicle): Vehicle {
        vehicle.id = id
        vehicles[id] = vehicle
        return vehicle
    }

    fun deleteVehicle(id: Long): Vehicle? = vehicles.remove(id)

    @PreDestroy
    fun saveVehicleData() {
        vehicleFileStore.saveDataToFile(vehicleFileStore, vehicles)
    }

    @PostConstruct
    fun vehicleDataToHashmap() {
        vehicleFileStore.loadDataToHashmap(vehicleFileStore, vehicles, id)
    }
}