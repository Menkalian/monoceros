rootProject.name = "app"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven {
            name = "artifactory-menkalian"
            url = uri("http://server.menkalian.de:8081/artifactory/menkalian")
            isAllowInsecureProtocol = true
        }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:7.0.1")
            }
        }
    }
}
