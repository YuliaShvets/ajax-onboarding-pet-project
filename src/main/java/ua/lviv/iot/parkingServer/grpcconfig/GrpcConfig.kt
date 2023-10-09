package ua.lviv.iot.parkingServer.grpcconfig

import io.grpc.BindableService
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfig(
    @Value("\${grpc.server.port}")
    private val grpcPort: Int,
) {

    @Bean
    fun grpcServer(
        grpcServices: List<BindableService>
    ): Server =
        ServerBuilder
            .forPort(grpcPort)
            .apply {
                grpcServices.forEach { addService(it) }
            }
            .build()
            .start()

    @Bean(destroyMethod = "shutdown")
    fun grpcChannel(): ManagedChannel =
        ManagedChannelBuilder
            .forTarget("localhost:$grpcPort")
            .usePlaintext()
            .build()
}
