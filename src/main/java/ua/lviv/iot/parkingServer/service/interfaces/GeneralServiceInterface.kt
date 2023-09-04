package ua.lviv.iot.parkingServer.service.interfaces

interface GeneralServiceInterface<T, ID> {

    fun findAllEntities(): List<T>

    fun findEntityById(id: Long): T

    fun addEntity(entity: T): T

    fun updateEntity(id: Long, entity: T): T

    fun deleteEntity(id: Long)

}
