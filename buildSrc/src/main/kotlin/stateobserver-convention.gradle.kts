plugins {
    `java`
    `maven-publish`
    `idea`
    `eclipse`
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)

    withSourcesJar()
    withJavadocJar()
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

val libs = project.versionCatalogs.find("libs")

val version = libs.get().findVersion("stateobserver").get()

val minecraftVersion = libs.get().findVersion("minecraft").get()
val minecraftVersionRange = libs.get().findVersion("minecraft.range").get()
val neoforgeVersion = libs.get().findVersion("neoforge").get()
val neoforgeVersionRange = libs.get().findVersion("neoforge.range").get()
val neoforgeLoaderRange = libs.get().findVersion("neoforge.loader.range").get()
val fapiVersion = libs.get().findVersion("fabric.api").get()
val fabricVersion = libs.get().findVersion("fabric").get()

tasks.withType<Jar>().configureEach {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_StateObserver" }
    }

    manifest {
        attributes(mapOf(
            "Specification-Title"     to "StateObserver",
            "Specification-Vendor"    to "Favouriteless",
            "Specification-Version"   to version,
            "Implementation-Title"    to "StateObserver",
            "Implementation-Version"  to version,
            "Implementation-Vendor"   to "Favouriteless",
            "Built-On-Minecraft"      to minecraftVersion
        ))
    }
}

tasks.withType<JavaCompile>().configureEach {
    this.options.encoding = "UTF-8"
    this.options.release.set(21)
}

tasks.withType<ProcessResources>().configureEach {
    val expandProps = mapOf(
        "version" to version,
        "minecraft_version" to minecraftVersion,
        "neoforge_version" to neoforgeVersion,
        "neoforge_version_range" to neoforgeVersionRange,
        "neoforge_loader_range" to neoforgeLoaderRange,
        "minecraft_version_range" to minecraftVersionRange,
        "fabric_api_version" to fapiVersion,
        "fabric_loader_version" to fabricVersion
    )

    filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
        expand(expandProps)
    }

    inputs.properties(expandProps)
}

// Disables Gradle's custom module metadata from being published to maven. The
// metadata includes mapped dependencies which are not reasonably consumable by
// other mod developers.
tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}

publishing {
    repositories {
        if (System.getenv("FAVOURITELESS_MAVEN_USER") == null && System.getenv("FAVOURITELESS_MAVEN_PASS") == null) {
            mavenLocal()
        }
        else maven {
            name = "FavouritelessReleases"
            url = uri("https://maven.favouriteless.net/releases")

            credentials {
                username = System.getenv("FAVOURITELESS_MAVEN_USER")
                password = System.getenv("FAVOURITELESS_MAVEN_PASS")
            }
        }
    }
}