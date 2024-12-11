import com.diluv.schoomp.Webhook
import com.diluv.schoomp.message.Message
import com.diluv.schoomp.message.embed.Embed
import java.io.IOException

plugins {
    id("stateobserver-convention")
    alias(libs.plugins.moddevgradle)
}

version = libs.versions.stateobserver.get()
val mcVersion = libs.versions.minecraft.asProvider().get()

base {
    archivesName = "stateobserver-common-${mcVersion}"
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

tasks.create("postDiscord") {
    group = "publishing"
    doLast {
        try {
            if(System.getenv("STATEOBSERVER_RELEASE_WEBHOOK") != null) {
                val webhook = Webhook(System.getenv("STATEOBSERVER_RELEASE_WEBHOOK"), "StateObserver Gradle Upload")

                val message = Message()
                message.username = "Elaina"
                message.avatarUrl = "https://i.imgur.com/I455GSr.jpeg"
                message.content = "StateObserver $version for Minecraft $mcVersion has been published!"

                val changeString = rootProject.file("changelog.txt").readText(Charsets.UTF_8).replace("\r", "")

                val embed = Embed()
                embed.title = "Changelog"
                embed.description = if (changeString.length < 4096) changeString else "The changelog was too long to embed, you can view it on the " +
                        "[GitHub Release](https://github.com/Favouriteless/StateObserver/releases/tag/v$version-release), " +
                        "[CurseForge](https://www.curseforge.com/minecraft/mc-mods/stateobserver/files/all) or " +
                        "[Modrinth](https://modrinth.com/mod/stateobserver/versions)."
                embed.color = 0x03A1FC

                message.addEmbed(embed)
                webhook.sendMessage(message)
            }
        } catch (e: IOException) {
            project.logger.error("Failed to push CF Discord webhook: " + e.message)
        }
    }
}

tasks.named<DefaultTask>("publish").configure {
    finalizedBy("postDiscord")
}