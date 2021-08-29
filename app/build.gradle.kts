plugins {
    id("com.android.application")
    kotlin("android") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.20"
}

repositories {
    mavenCentral()
    google()
    maven {
        name = "artifactory-menkalian"
        url = uri("http://server.menkalian.de:8081/artifactory/menkalian")
        isAllowInsecureProtocol = true
    }
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "de.menkalian.monoceros"

        minSdk = 24
        targetSdk = 30

        versionCode = 1
        versionName = "0.0.1"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
                         )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    // Ktor
    val ktor_version = "1.6.2"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-json:$ktor_version")

    implementation("de.menkalian.vela:tool-template:1.0.0")

    // Android Libs
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("com.google.android.material:material:1.5.0-alpha02")
}
