group = rootProject.group.toString() + ".core"

tasks {
    getByName("jar", Jar::class) {
        archiveBaseName.set(rootProject.name + "-Core")
    }
}
