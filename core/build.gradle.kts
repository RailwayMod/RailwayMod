import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    //Latest version: https://fabricmc.net/develop/
    id("fabric-loom") version "0.11-SNAPSHOT"
}

group = rootProject.group.toString() + ".core"

dependencies {
    implementation(kotlin("stdlib"))

    //Minecraft API, using fabric's mapping
    //Latest version: https://fabricmc.net/develop/
    add("minecraft", "com.mojang:minecraft:1.18.2")
    add("mappings", loom.officialMojangMappings())
}

tasks {
    create("replaceValuePlaceholder", Copy::class) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(File(buildDir, "tempSrc/main/kotlin/"))
        into(File(projectDir, "src/main/kotlin/"))

        mapOf(
            "modId" to rootProject.name.toLowerCase(),
            "modName" to rootProject.name,
            "modVersion" to rootProject.version
        ).let { filter<ReplaceTokens>("tokens" to it) }
    }

    compileKotlin {
        destinationDirectory.set(File(buildDir, "classes/java/main"))

        dependsOn("replaceValuePlaceholder")
    }

    create("includeResources", Copy::class) {
        from(sourceSets.main.get().resources.srcDirs)
        include("**")
        into(File(buildDir, "resources/main/"))
    }

    //Gradle didn't create jar by task "jar"
    create("createLibraryJar", Jar::class) {
        archiveBaseName.set(project.name)

        dependsOn(getByName("includeResources"))
    }

    getByName("jar").dependsOn(getByName("createLibraryJar"))
}
