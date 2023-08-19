package ua.lviv.iot.parkingServer.model

import ua.lviv.iot.parkingServer.model.enums.VehicleType

data class Vehicle(
    var vehicleId: Long,
    var number: String,
    var typeOfVehicle: VehicleType,
    var durationOfUseOfParkingSpot: Double, // in hours
    var isTicketReceived: Boolean
) : Record() {


    override fun getHeaders(): String =
        listOf(
            "Vehicle id",
            "Number",
            "Vehicle Type",
            " Duration of use parking spot",
            "Is ticket received"
        ).joinToString(separator = ", ")

    override fun toCSV(): String = "$vehicleId, $number, $typeOfVehicle, $durationOfUseOfParkingSpot, $isTicketReceived"
}