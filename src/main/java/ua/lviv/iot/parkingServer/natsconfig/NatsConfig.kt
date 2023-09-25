package ua.lviv.iot.parkingServer.natsconfig

import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfig {

    @Bean
    fun natsConnection(
        @Value("\${nats.connection.url}") natsUrl: String
    ): Connection = Nats.connect(natsUrl)
}
