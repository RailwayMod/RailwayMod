plugins {
    //Latest version: https://fabricmc.net/develop/
    id("fabric-loom") version "0.11-SNAPSHOT"
}

group = rootProject.group.toString() + ".fabric"

dependencies {
    api(fileTree(project(":core").dependencyProject.tasks.jar.get().archiveFile.get().asFile))

    api(kotlin("stdlib"))

    //Fabric
    //Latest version: https://fabricmc.net/develop/
    //fabric-language-kotlin: https://github.com/FabricMC/fabric-language-kotlin
    add("minecraft", "com.mojang:minecraft:1.18.2")
    add("mappings", loom.officialMojangMappings())
    add("modImplementation", "net.fabricmc:fabric-loader:0.13.3")
    add("modImplementation", "net.fabricmc.fabric-api:fabric-api:0.47.10+1.18.2")
    add("modImplementation", "net.fabricmc:fabric-language-kotlin:1.7.1+kotlin.1.6.10")
}

tasks {
    jar {
        destinationDirectory.set(ext.get("libsDir") as File)
        archiveClassifier.set("mapped")

        from(configurations.api.get().apply { isCanBeResolved = true }.map { if (it.isDirectory) it else zipTree(it) })
    }

    remapJar {
        destinationDirectory.set(ext.get("libsDir") as File)

        inputFile.set(jar.get().archiveFile.get())

        archiveBaseName.set(rootProject.name)
        archiveVersion.set("${rootProject.version}+${project.name}")
        archiveClassifier.set("")
        archiveExtension.set("jar")

        dependsOn("jar")
    }
}
