package ua.lviv.iot.parkingServer

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfig(
    @Value("\${grpc.server.port}")
    var grpcPort: Int
) {
    @Bean(destroyMethod = "shutdown")
    fun grpcChannel(): ManagedChannel =
        ManagedChannelBuilder
            .forTarget("localhost:$grpcPort")
            .usePlaintext()
            .build()

}
