plugins {
    //Latest version: https://fabricmc.net/develop/
    id("fabric-loom") version "0.11-SNAPSHOT"
}

group = rootProject.group.toString() + ".fabric"

dependencies {
    api(project(":core"))

    api(kotlin("stdlib"))

    //Fabric
    //Latest version: https://fabricmc.net/develop/
    //fabric-language-kotlin: https://github.com/FabricMC/fabric-language-kotlin
    add("minecraft", "com.mojang:minecraft:1.18.2")
    add("mappings", "net.fabricmc:yarn:1.18.2+build.2")
    add("modImplementation", "net.fabricmc:fabric-loader:0.13.3")
    add("modImplementation", "net.fabricmc.fabric-api:fabric-api:0.47.10+1.18.2")
    add("modImplementation", "net.fabricmc:fabric-language-kotlin:1.7.1+kotlin.1.6.10")
}

tasks {
    getByName("jar", Jar::class) {
        archiveBaseName.set(rootProject.name + "-Fabric")

        from(configurations.api.get().apply { isCanBeResolved = true }.map { if (it.isDirectory) it else zipTree(it) })
    }
}