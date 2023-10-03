package ua.lviv.iot.parkingServer.grpcservice


import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component
import ua.lviv.iot.VehicleGrpcServiceGrpc.VehicleGrpcServiceImplBase
import ua.lviv.iot.VehicleOuterClass
import ua.lviv.iot.parkingServer.converter.VehicleConverter
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Component
class VehicleGrpcService(
    private val service: VehicleServiceInterface,
    private val converter: VehicleConverter
) : VehicleGrpcServiceImplBase() {

    override fun createVehicle(
        request: VehicleOuterClass.VehicleRequest,
        responseObserver: StreamObserver<VehicleOuterClass.VehicleResponse>
    ) {
        val vehicle = converter.protoToVehicle(request.vehicle)
        val vehicleToProto = converter.vehicleToProto(service.addEntity(vehicle).block()!!)

        val builtResponse = VehicleOuterClass.VehicleResponse.newBuilder().setVehicle(vehicleToProto).build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()
    }

    override fun getVehicleById(
        request: VehicleOuterClass.GetByIdVehicleRequest,
        responseObserver: StreamObserver<VehicleOuterClass.VehicleResponse>
    ) {
        val vehicleId = request.vehicleId
        val vehicleToProto = converter.vehicleToProto(service.findEntityById(vehicleId).block()!!)

        val builtResponse = VehicleOuterClass.VehicleResponse.newBuilder().setVehicle(vehicleToProto).build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()

    }

    override fun updateVehicle(
        request: VehicleOuterClass.UpdateVehicleRequest,
        responseObserver: StreamObserver<VehicleOuterClass.VehicleResponse>
    ) {
        val vehicle =
            converter
                .protoToVehicle(request.vehicle)
                .apply { id = request.vehicleId }
        val vehicleToProto = converter.vehicleToProto(service.updateEntity(vehicle).block()!!)
        val builtResponse = VehicleOuterClass.VehicleResponse.newBuilder().setVehicle(vehicleToProto).build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()

    }

    override fun deleteVehicle(
        request: VehicleOuterClass.DeleteVehicleRequest,
        responseObserver: StreamObserver<VehicleOuterClass.DeleteVehicleResponse>
    ) {
        service.deleteEntity(request.vehicleId).block()
        val builtResponse = VehicleOuterClass.DeleteVehicleResponse.newBuilder().build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()
    }
}
