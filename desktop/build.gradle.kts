plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("de.menkalian.vela.keygen") version "1.2.1"
    kotlin("jvm") version "1.5.21"
}

group = "de.menkalian.monoceros"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11
application.mainClass.set("de.menkalian.monoceros.desktop.MainKt")

repositories {
    mavenCentral()
    maven {
        name = "artifactoryMenkalian"
        url = uri("http://server.menkalian.de:8081/artifactory/menkalian")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("de.menkalian.vela:tool-template:1.0.0")
}

keygen {
    finalLayerAsString = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}
