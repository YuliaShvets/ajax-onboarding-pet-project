package ua.lviv.iot.parkingServer.grpcconfig

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfig {

    @Bean
    fun grpcServer(
        @Value("\${grpc.server.port}")
        grpcPort: Int,
        grpcServices: List<BindableService>
    ): Server =
        ServerBuilder
            .forPort(grpcPort)
            .apply {
                grpcServices.forEach { addService(it) }
            }
            .build()
            .start()
}
