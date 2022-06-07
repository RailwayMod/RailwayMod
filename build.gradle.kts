import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "dev.siro256.mcmod.railwaymod"

allprojects {
    apply(plugin = "kotlin")

    version = "0.0.1-SNAPSHOT"

    repositories {
        maven { url = uri("https://repo.siro256.dev/repository/maven-public/") }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.apply {
                allWarningsAsErrors = true
                jvmTarget = "17"
            }
            sourceCompatibility = "17"
            targetCompatibility = "17"
        }

        withType<ProcessResources> {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }

        withType<Jar> {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }

        create("includeLicense", Copy::class) {
            destinationDir = File(project.buildDir, "resources/main/")

            from(rootProject.file("LICENSE").path) {
                rename { "LICENSE_${rootProject.name}" }
            }
        }

        processResources.get().finalizedBy("includeLicense")
    }

    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }

    ext {
        //credits
        listOf<String>(
        ).let { set("credits", it) }

        //authors
        listOf(
            "Siro_256 Twitter: @ffffff_256"
        ).let { set("authors", it) }

        //description
        set("description", "Realistic train mod for Minecraft")
    }
}
