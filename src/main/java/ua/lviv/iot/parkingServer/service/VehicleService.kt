package ua.lviv.iot.parkingServer.service

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import org.springframework.stereotype.Service
import ua.lviv.iot.parkingServer.datastorage.VehicleFileStore
import ua.lviv.iot.parkingServer.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Service
class VehicleService(private val vehicleFileStore: VehicleFileStore) : VehicleServiceInterface {
    private val vehicles: MutableMap<Long, Vehicle> = ConcurrentHashMap()
    private var id: AtomicLong = AtomicLong(1L)

    override fun findAllEntities(): List<Vehicle> = vehicles.values.toList()

    override fun findEntityById(id: Long): Vehicle =
        vehicles[id] ?: throw EntityNotFoundException("Vehicle with id=$id not found")

    override fun addEntity(entity: Vehicle): Vehicle {
        val newId = id.getAndIncrement()
        entity.id = newId
        vehicles[newId] = entity
        return entity
    }

    override fun updateEntity(id: Long, entity: Vehicle): Vehicle {
        entity.id = id
        vehicles[id] = entity
        return entity
    }

    override fun deleteEntity(id: Long): Vehicle =
        vehicles.remove(id) ?: throw EntityNotFoundException("Vehicle with id=$id doesn't exist")

    @PostConstruct
    override fun entityDataToHashMap() {
        vehicleFileStore.loadDataToHashmap(vehicleFileStore, vehicles, id)
    }

    @PreDestroy
    override fun saveEntityData() {
        vehicleFileStore.saveDataToFile(vehicleFileStore, vehicles)
    }

}
