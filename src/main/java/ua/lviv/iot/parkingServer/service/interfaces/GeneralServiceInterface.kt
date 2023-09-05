package ua.lviv.iot.parkingServer.service.interfaces


interface GeneralServiceInterface<T, ID> {

    fun findAllEntities(): List<T>

    fun findEntityById(id: String): T

    fun addEntity(entity: T): T

    fun updateEntity(id: String, entity: T): T

    fun deleteEntity(id: String)

}
