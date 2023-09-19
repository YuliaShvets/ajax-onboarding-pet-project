package ua.lviv.iot.parkingServer.nats.config

import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(basePackages = ["ua.lviv.iot.parkingServer"])
class NatsConfig(@Value("\${nats.connection.url}") private val natsUrl: String) {
    @Bean
    fun natsConnection(): Connection = Nats.connect(natsUrl)
}
