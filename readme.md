# StateObserver

StateObserver is a lightweight and user-friendly API for observing BlockState changes without needing to explicitly check for them.

## Getting Started

### Installation

**NOTE:** On versions below 1.20.1, use [CurseMaven](https://www.cursemaven.com/) or [Modrinth Maven](https://support.modrinth.com/en/articles/8801191-modrinth-maven) as the Favouriteless Maven repo was only created on that version.

StateObserver is incredibly easy to install-- you just need to insert a couple lines into your build.gradle(.kts) file.

First, add the maven repository to your `build.gradle` file.

```groovy
// This is the repositories block in your build.gradle. NOT the one in publishing, or in buildscript. If it does not exist already, create it.
repositories {
	maven {
		name = 'Favouriteless Maven'
		url 'https://maven.favouriteless.net/releases'
	}
}
```
Then, add StateObserver to the dependencies block in your `build.gradle` file (replace forge with the loader).
```groovy
dependencies {
	implementation "net.favouriteless.stateobserver:stateobserver-forge-${minecraft_version}:${stateobserver_version}"
}
```
Lastly, add a `stateobserver_version` property to your `gradle.properties` file. The latest release versions for StateObserver can be found on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/stateobserver) and [Modrinth](https://modrinth.com/mod/stateobserver).
```groovy
stateobserver_version=1.5.0
```