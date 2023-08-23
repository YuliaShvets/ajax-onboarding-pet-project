package ua.lviv.iot.parkingServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParkingServerApplication

fun main(args: Array<String>) {
    runApplication<ParkingServerApplication>(*args)
}