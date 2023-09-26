package ua.lviv.iot.parkingServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class ParkingServerApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<ParkingServerApplication>(*args)
}
