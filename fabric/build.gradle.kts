import org.apache.tools.ant.filters.ReplaceTokens

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

    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            include("**")

            @Suppress("UNCHECKED_CAST")
            mapOf(
                "modId" to rootProject.name.toLowerCase(),
                "modName" to rootProject.name,
                "modVersion" to rootProject.version,
                "description" to ext.get("description"),
                "license" to ext.get("license"),
                "modPage" to ext.get("modPage"),
                "issueTrackerURL" to ext.get("issueTracker"),
                "sourcesURL" to ext.get("sourcesURL"),
                "authors" to (ext.get("authors") as List<String>).let {
                    if (it.isNotEmpty()) {
                        it.joinToString(
                            prefix = "\n        {\n            \"name\": \"",
                            separator = "\"\n        },\n        {\n            \"name\": \"",
                            postfix = "\"\n        }\n    "
                        )
                    } else ""
                },
                "contributors" to (ext.get("contributors") as List<String>).let {
                    if (it.isNotEmpty()) {
                        it.joinToString(
                            prefix = "\n        {\n            \"name\": \"",
                            separator = "\"\n        },\n        {\n            \"name\": \"",
                            postfix = "\"\n        }\n    "
                        )
                    } else ""
                }
            ).let { filter<ReplaceTokens>("tokens" to it) }
        }
    }

    compileKotlin.get().dependsOn(":core:jar")
    prepareRemapJar.get().dependsOn(":fabric:jar")
}
