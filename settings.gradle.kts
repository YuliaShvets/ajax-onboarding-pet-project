plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "ParkingServer"

include("nats")
include("parking-submodule")
include("common-submodule")
include("vehicle-submodule")
include("parking-spot-submodule")
