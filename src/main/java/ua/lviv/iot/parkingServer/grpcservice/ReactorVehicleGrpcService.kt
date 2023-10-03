package ua.lviv.iot.parkingServer.grpcservice

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ReactorVehicleGrpcServiceGrpc
import ua.lviv.iot.VehicleOuterClass.DeleteVehicleRequest
import ua.lviv.iot.VehicleOuterClass.DeleteVehicleResponse
import ua.lviv.iot.VehicleOuterClass.GetByIdVehicleRequest
import ua.lviv.iot.VehicleOuterClass.UpdateVehicleRequest
import ua.lviv.iot.VehicleOuterClass.VehicleRequest
import ua.lviv.iot.VehicleOuterClass.VehicleResponse
import ua.lviv.iot.parkingServer.converter.VehicleConverter
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Component
class ReactorVehicleGrpcService(
    private val service: VehicleServiceInterface,
    private val converter: VehicleConverter
) : ReactorVehicleGrpcServiceGrpc.VehicleGrpcServiceImplBase() {

    override fun createVehicle(
        request: Mono<VehicleRequest>,
    ): Mono<VehicleResponse> {
        return request.flatMap { service.addEntity(converter.protoToVehicle(it.vehicle)) }
            .map { converter.vehicleToProto(it) }
            .map { VehicleResponse.newBuilder().setVehicle(it).build() }
    }

    override fun getVehicleById(
        request: Mono<GetByIdVehicleRequest>,
    ): Mono<VehicleResponse> {
        return request.flatMap { service.findEntityById(it.vehicleId) }
            .map { converter.vehicleToProto(it) }
            .map { VehicleResponse.newBuilder().setVehicle(it).build() }
    }

    override fun updateVehicle(
        request: Mono<UpdateVehicleRequest>,
    ): Mono<VehicleResponse> {
        return request.flatMap { updateRequest ->
            val vehicle = converter.protoToVehicle(updateRequest.vehicle)
            vehicle.id = updateRequest.vehicleId

            service.updateEntity(vehicle)
                .map { updatedVehicle -> converter.vehicleToProto(updatedVehicle) }
                .map { vehicleProto ->
                    VehicleResponse.newBuilder()
                        .setVehicle(vehicleProto)
                        .build()
                }
        }

    }

    override fun deleteVehicle(
        request: Mono<DeleteVehicleRequest>,
    ): Mono<DeleteVehicleResponse> {
        return request.flatMap { service.deleteEntity(it.vehicleId) }
            .map { DeleteVehicleResponse.newBuilder().build() }
    }
}
