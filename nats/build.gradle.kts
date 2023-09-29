plugins {
    idea
    id("com.google.protobuf") version "0.9.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.24.2")
    implementation("com.google.protobuf:protobuf-java-util:3.20.1")
    implementation("io.grpc:grpc-stub:1.58.0")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("io.grpc:grpc-netty:1.58.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
}

kotlin {
    jvmToolchain(17)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.20.1"
    }

    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.46.0"
        }
    }
    generateProtoTasks {
        all().configureEach {
            generateDescriptorSet = true
            descriptorSetOptions.includeImports = true
        }
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}
