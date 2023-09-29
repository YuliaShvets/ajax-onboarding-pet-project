package ua.lviv.iot.parkingServer.grpcservice

import com.example.VehicleGrpcServiceGrpc.VehicleGrpcServiceImplBase
import com.example.VehicleOuterClass
import com.example.VehicleOuterClass.DeleteVehicleResponse
import com.example.VehicleOuterClass.GetByIdVehicleRequest
import com.example.VehicleOuterClass.UpdateVehicleRequest
import com.example.VehicleOuterClass.VehicleRequest
import com.example.VehicleOuterClass.VehicleResponse
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.converter.VehicleConverter
import ua.lviv.iot.parkingServer.service.interfaces.VehicleServiceInterface

@Component
class VehicleGrpcService(
    private val service: VehicleServiceInterface,
    private val converter: VehicleConverter
) :
    VehicleGrpcServiceImplBase() {

    override fun createVehicle(
        request: VehicleRequest,
        responseObserver: StreamObserver<VehicleResponse>
    ) {
        val vehicle = converter.protoToVehicle(request.vehicle)
        val vehicleToProto = converter.vehicleToProto(service.addEntity(vehicle).block()!!)

        val builtResponse = VehicleResponse.newBuilder().setVehicle(vehicleToProto).build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()
    }

    override fun getVehicleById(request: GetByIdVehicleRequest, responseObserver: StreamObserver<VehicleResponse>) {
        val vehicleId = request.vehicleId
        val vehicleToProto = converter.vehicleToProto(service.findEntityById(vehicleId).block()!!)

        val builtResponse = VehicleResponse.newBuilder().setVehicle(vehicleToProto).build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()

    }

    override fun updateVehicle(
        request: UpdateVehicleRequest,
        responseObserver: StreamObserver<VehicleResponse>
    ) {
        val vehicle =
            converter
                .protoToVehicle(request.vehicle)
                .apply { id = request.vehicleId }
        val vehicleToProto = converter.vehicleToProto(service.updateEntity(vehicle).block()!!)
        val builtResponse = VehicleResponse.newBuilder().setVehicle(vehicleToProto).build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()

    }

    override fun deleteVehicle(
        request: VehicleOuterClass.DeleteVehicleRequest,
        responseObserver: StreamObserver<DeleteVehicleResponse>
    ) {
        service.deleteEntity(request.vehicleId).block()
        val builtResponse = DeleteVehicleResponse.newBuilder().build()
        responseObserver.onNext(builtResponse)
        responseObserver.onCompleted()
    }
}
