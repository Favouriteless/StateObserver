plugins {
    alias(libs.plugins.minotaur) apply false
    alias(libs.plugins.curseforgegradle) apply false

    // Required for NeoGradle
    alias(libs.plugins.ideaext)
}

subprojects {

    repositories {
        maven("https://maven.favouriteless.net/releases") { name = "Favouriteless Maven" }
    }

}

