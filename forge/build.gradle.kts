import net.minecraftforge.gradle.userdev.UserDevExtension
import org.apache.tools.ant.filters.ReplaceTokens

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
    api(fileTree(project(":core").dependencyProject.tasks.jar.get().archiveFile.get().asFile))

    api(kotlin("stdlib"))

    //Forge
    add("minecraft", "net.minecraftforge:forge:1.18.2-40.0.12")
}

configure<UserDevExtension> {
    mappings("official", "1.18.2")
}

tasks {
    jar {
        destinationDirectory.set(ext.get("libsDir") as File)
        archiveClassifier.set("")

        from(
            configurations.api.get().copy()
                .apply { isCanBeResolved = true }
                .map { if (it.isDirectory) it else zipTree(it) }
        )

        finalizedBy("reobfJar")
    }

    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            include("**")

            @Suppress("UNCHECKED_CAST")
            mapOf(
                "modId" to rootProject.name.toLowerCase(),
                "modName" to rootProject.name,
                "modVersion" to rootProject.version,
                "issueTrackerURL" to ext.get("issueTracker"),
                "updateJsonURL" to ext.get("forgeUpdateJsonURL"),
                "modPage" to ext.get("modPage"),
                "license" to ext.get("license"),
                "authors" to
                        listOf(
                            ext.get("authors") as List<String>,
                            ext.get("contributors") as List<String>
                        ).flatten().joinToString("\n"),
                "description" to ext.get("description")
            ).let { filter<ReplaceTokens>("tokens" to it) }
        }
    }

    compileKotlin.get().dependsOn(":core:jar")
}
