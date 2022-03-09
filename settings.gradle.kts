pluginManagement {
    repositories {
        maven { url = uri("https://repo.siro256.dev/repository/maven-public/") }
    }

    plugins {
        kotlin("jvm") version "1.6.10"
    }
}

rootProject.name = "RailwayMod"

include("core", "forge", "fabric")
