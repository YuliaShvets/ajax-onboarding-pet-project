package ua.lviv.iot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParkingServerApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<ParkingServerApplication>(*args)
}
