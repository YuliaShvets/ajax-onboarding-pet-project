package ua.lviv.iot.parkingServer.kafka

import com.google.protobuf.GeneratedMessageV3
import com.google.protobuf.Parser
import java.util.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.kafka.receiver.ReceiverOptions
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotRequest

@EnableKafka
@Configuration
class KafkaConsumer {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var kafkaAddress: String

    @Bean
    fun kafkaConsumerOptions(): ReceiverOptions<String, GeneratedMessageV3> {
        val basicReceiverOptions: ReceiverOptions<String, GeneratedMessageV3> =
            ReceiverOptions.create(
                mapOf(
                    Pair(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress),
                    Pair(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java),
                    Pair(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ProtobufDeserializer::class.java),
                    Pair(ConsumerConfig.GROUP_ID_CONFIG, "parking")
                )
            )
        return basicReceiverOptions.subscription(Collections.singletonList("ADDED_AVAILABLE_PARKING_SPOT"))
    }

    @Bean
    fun kafkaConsumerTemplate(
        kafkaReceiverOptions: ReceiverOptions<String, GeneratedMessageV3>
    ): ReactiveKafkaConsumerTemplate<String, GeneratedMessageV3> {
        return ReactiveKafkaConsumerTemplate(kafkaConsumerOptions())
    }
}

class ProtobufDeserializer : Deserializer<GeneratedMessageV3> {

    private val topicToParserMap = HashMap<String, Parser<CreateParkingSpotRequest>>()

    private val parser: Parser<CreateParkingSpotRequest> =
        CreateParkingSpotRequest.parser()

    override fun deserialize(topic: String, data: ByteArray): GeneratedMessageV3? {
        topicToParserMap[topic] = parser
        val parser = topicToParserMap[topic]
        return parser?.parseFrom(data)
    }
}
