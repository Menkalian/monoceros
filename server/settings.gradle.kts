rootProject.name = "server"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "artifactoryMenkalian"
            url = uri("http://server.menkalian.de:8081/artifactory/menkalian")
            isAllowInsecureProtocol = true
        }
    }
}