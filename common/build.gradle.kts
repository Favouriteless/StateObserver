plugins {
    id("stateobserver-convention")
    alias(libs.plugins.moddevgradle)
}

version = libs.versions.stateobserver.get()

base {
    archivesName = "stateobserver-common-${libs.versions.minecraft.asProvider().get()}"
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()

//    accessTransformers.files.setFrom("src/main/resources/META-INF/accesstransformer.cfg")

    parchment {
        minecraftVersion = libs.versions.parchment.minecraft.get()
        mappingsVersion = libs.versions.parchment.asProvider().get()
    }
}

dependencies {
    compileOnly(libs.mixin)
    compileOnly(libs.mixinextras.common)
}

publishing {
    publications {
        create<MavenPublication>("stateobserver") {
            from(components["java"])
            artifactId = base.archivesName.get()
        }
    }
}