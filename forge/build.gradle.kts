import net.minecraftforge.gradle.userdev.UserDevExtension

buildscript {
    repositories {
        maven { url = uri("https://repo.siro256.dev/repository/maven-public/") }
    }

    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:5.1.+") {
            isChanging = true
        }
    }
}

apply(plugin = "net.minecraftforge.gradle")

group = rootProject.group.toString() + ".forge"

dependencies {
    api(project(":core"))

    api(kotlin("stdlib"))

    //Forge
    add("minecraft", "net.minecraftforge:forge:1.18.2-40.0.12")
}

configure<UserDevExtension> {
    mappings("official", "1.18.2")
}

tasks {
    getByName("jar", Jar::class) {
        archiveBaseName.set(rootProject.name + "-Forge")

        from(configurations.api.get().apply { isCanBeResolved = true }.map { if (it.isDirectory) it else zipTree(it) })

        dependsOn(":core:jar")
    }
}
