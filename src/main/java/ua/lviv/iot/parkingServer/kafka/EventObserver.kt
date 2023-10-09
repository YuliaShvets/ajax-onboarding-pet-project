package ua.lviv.iot.parkingServer.kafka

import io.grpc.ManagedChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingSpotOuterClass.ParkingSpotRequest
import ua.lviv.iot.ReactorKafkaParkingSpotServiceGrpc

@Component
class EventObserver(private val grpcChannel: ManagedChannel) {

    private lateinit var stub: ReactorKafkaParkingSpotServiceGrpc.ReactorKafkaParkingSpotServiceStub

    fun observe() {
        stub = ReactorKafkaParkingSpotServiceGrpc.newReactorStub(grpcChannel)
        stub.createParkingSpot(Flux.from(Mono.just(ParkingSpotRequest.getDefaultInstance())))
            .doOnNext { println(it) }
            .subscribe()
        grpcChannel.shutdown()
    }
}
