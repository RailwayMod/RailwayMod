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

val tmpSrcDir = File(buildDir, "tmpSrc/main/kotlin")

tasks {
    create("cloneSource", Copy::class) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(File(projectDir, "src/main/kotlin/"))
        into(tmpSrcDir)

        mapOf(
            "modId" to rootProject.name.toLowerCase(),
            "modName" to rootProject.name,
            "modVersion" to rootProject.version
        ).let { filter<ReplaceTokens>("tokens" to it) }
    }

    compileKotlin {
        doFirst { source = fileTree(tmpSrcDir) }
        destinationDirectory.set(File(buildDir, "classes/java/main"))

        dependsOn("cloneSource")
    }


}
