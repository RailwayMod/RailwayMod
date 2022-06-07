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

            archiveBaseName.set(rootProject.name)
            archiveAppendix.set("")
            archiveVersion.set("${rootProject.version}+${project.name}")
            archiveClassifier.set("")
            archiveExtension.set("jar")

            dependsOn("includeLicense")
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
        set("libsDir", File(rootProject.buildDir, "libs/"))

        set("modPage", "https://github.com/RailwayMod/")
        set("sourcesURL", "https://github.com/RailwayMod/")
        set("issueTracker", "https://github.com/RailwayMod/RailwayMod/issues")
        set("forgeUpdateJsonURL", "")
        set("license", "GNU General Public License v3.0")
        set("description", "Realistic train mod for Minecraft")

        //authors
        listOf(
            "Siro_256 Twitter: @ffffff_256"
        ).let { set("authors", it) }

        //contributors
        listOf<String>(
        ).let { set("contributors", it) }
    }
}
