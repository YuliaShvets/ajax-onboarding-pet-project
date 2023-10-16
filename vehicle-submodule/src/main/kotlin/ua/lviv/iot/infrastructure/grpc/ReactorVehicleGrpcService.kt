package ua.lviv.iot.infrastructure.grpc

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ReactorVehicleGrpcServiceGrpc
import ua.lviv.iot.VehicleOuterClass.CreateVehicleRequest
import ua.lviv.iot.VehicleOuterClass.CreateVehicleResponse
import ua.lviv.iot.VehicleOuterClass.DeleteVehicleRequest
import ua.lviv.iot.VehicleOuterClass.DeleteVehicleResponse
import ua.lviv.iot.VehicleOuterClass.GetByIdVehicleRequest
import ua.lviv.iot.VehicleOuterClass.UpdateVehicleRequest
import ua.lviv.iot.application.proto.converter.VehicleConverter
import ua.lviv.iot.application.service.VehicleServiceInPort

@Component
class ReactorVehicleGrpcService(
    private val service: VehicleServiceInPort,
    private val converter: VehicleConverter
) : ReactorVehicleGrpcServiceGrpc.VehicleGrpcServiceImplBase() {

    override fun createVehicle(
        request: Mono<CreateVehicleRequest>,
    ): Mono<CreateVehicleResponse> {
        return request.flatMap { service.addEntity(converter.protoToVehicle(it.vehicle)) }
            .map { converter.vehicleToProto(it) }
            .map { CreateVehicleResponse.newBuilder().setVehicle(it).build() }
    }

    override fun getVehicleById(
        request: Mono<GetByIdVehicleRequest>,
    ): Mono<CreateVehicleResponse> {
        return request.flatMap { service.findEntityById(it.vehicleId) }
            .map { converter.vehicleToProto(it) }
            .map { CreateVehicleResponse.newBuilder().setVehicle(it).build() }
    }

    override fun updateVehicle(
        request: Mono<UpdateVehicleRequest>,
    ): Mono<CreateVehicleResponse> {
        return request.flatMap { updateRequest ->
            val vehicle = converter.protoToVehicle(updateRequest.vehicle)
            vehicle.id = updateRequest.vehicleId

            service.updateEntity(vehicle)
                .map { updatedVehicle -> converter.vehicleToProto(updatedVehicle) }
                .map { vehicleProto ->
                    CreateVehicleResponse.newBuilder()
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
