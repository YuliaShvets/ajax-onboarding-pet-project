package ua.lviv.iot.parkingServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
class ParkingServerApplication

fun main(args: Array<String>) {
    runApplication<ParkingServerApplication>(*args)
}