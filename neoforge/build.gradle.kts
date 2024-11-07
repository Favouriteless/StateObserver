import net.darkhax.curseforgegradle.TaskPublishCurseForge

plugins {
    id("stateobserver-convention")

    alias(libs.plugins.minotaur)
    alias(libs.plugins.curseforgegradle)
    alias(libs.plugins.moddevgradle)
}

version = libs.versions.stateobserver.get()
val mcVersion = libs.versions.minecraft.asProvider().get()

base {
    archivesName = "stateobserver-neoforge-${mcVersion}"
}

neoForge {
    version = libs.versions.neoforge.asProvider().get()

//    accessTransformers.files.setFrom(project(":common").file("src/main/resources/META-INF/accesstransformer.cfg"))

    parchment {
        minecraftVersion = libs.versions.parchment.minecraft.get()
        mappingsVersion = libs.versions.parchment.asProvider().get()
    }

    mods.create("stateobserver").sourceSet(project.sourceSets.getByName("main"))

    runs {
        configureEach {
            logLevel = org.slf4j.event.Level.DEBUG
        }

        create("client") {
            client()
            gameDirectory = file("runs/client")
            programArguments.addAll(
                "--username", "Favouriteless",
                "--uuid", "9410df73-6be3-41d5-a620-51b2e9be667b"
            )
        }

        create("server") {
            server()
            gameDirectory = file("runs/server")
            programArgument("--nogui")
        }

    }
}

dependencies {
    compileOnly( project(":common") )
}


tasks.withType<JavaCompile>().matching{!it.name.startsWith("neo")}.configureEach {
    source(project(":common").sourceSets.getByName("main").allSource)
}

tasks.named<Jar>("sourcesJar").configure {
    from(project(":common").sourceSets.getByName("main").allSource)
}

tasks.withType<Javadoc>().configureEach {
    source(project(":common").sourceSets.getByName("main").allJava)
}

tasks.withType<ProcessResources>().configureEach {
    from(project(":common").sourceSets.getByName("main").resources)
}

publishing {
    publications {
        create<MavenPublication>("stateobserver") {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN") ?: "Invalid/No API Token Found"

    projectId = "D1lhcTKz"
    versionName = "NeoForge ${mcVersion}"
    versionNumber.set(project.version.toString())
    uploadFile.set(tasks.jar)
    changelog.set(rootProject.file("changelog.txt").readText(Charsets.UTF_8))

    loaders.set(listOf("neoforge"))
    gameVersions.set(listOf(mcVersion))

    //debugMode = true
    //https://github.com/modrinth/minotaur#available-properties
}

tasks.register<TaskPublishCurseForge>("publishToCurseForge") {
    group = "publishing"
    apiToken = System.getenv("CURSEFORGE_TOKEN") ?: "Invalid/No API Token Found"

    val mainFile = upload(701213, tasks.jar)
    mainFile.releaseType = "release"
    mainFile.addModLoader("NeoForge")
    mainFile.addGameVersion(mcVersion)
    mainFile.addJavaVersion("Java 21")
    mainFile.changelog = rootProject.file("changelog.txt").readText(Charsets.UTF_8)

    //debugMode = true
    //https://github.com/Darkhax/CurseForgeGradle#available-properties
}


tasks.named<DefaultTask>("publish").configure {
    finalizedBy("modrinth")
    finalizedBy("publishToCurseForge")
}



