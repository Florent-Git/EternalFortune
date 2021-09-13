val exposedVersion: String by project
val koin_version = "3.1.2"

plugins {
	id("com.mineinabyss.conventions.kotlin")
	kotlin("plugin.serialization")
	id("com.mineinabyss.conventions.papermc")
	id("com.mineinabyss.conventions.publication")
}

repositories {
	mavenCentral()
	maven("https://repo.mineinabyss.com/releases")
	maven("https://jitpack.io")
}

dependencies {
	// Kotlin spice dependencies
	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json")
	compileOnly("com.charleskorn.kaml:kaml")

	// Database
	slim("org.jetbrains.exposed:exposed-core:$exposedVersion") { isTransitive = false }
	slim("org.jetbrains.exposed:exposed-dao:$exposedVersion") { isTransitive = false }
	slim("org.jetbrains.exposed:exposed-jdbc:$exposedVersion") { isTransitive = false }
	slim("org.jetbrains.exposed:exposed-java-time:$exposedVersion") { isTransitive = false }

	slim("io.insert-koin:koin-core:$koin_version")

	// Sqlite
	slim("org.xerial:sqlite-jdbc:3.30.1")

	// Kotlin JVM
	slim(kotlin("stdlib-jdk8"))

	// Shaded
	implementation("com.mineinabyss:idofront:1.17.1-0.6.23")
	implementation("com.github.okkero:Skedule:v1.2.6")

	// Geary
//	compileOnly ("com.mineinabyss:geary-core:0.6.48")
//	compileOnly ("com.mineinabyss:geary-platform-papermc:0.6.48")

//	slim("com.mineinabyss:geary-platform-papermc:0.6.49")
//	slim("com.mineinabyss:geary-commons-papermc:0.1.2")
//	slim("com.github.MineInAbyss:Geary-addons:master-SNAPSHOT")

	compileOnly("com.mineinabyss:geary-platform-papermc:0.6.51")

	testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
	testImplementation("org.hamcrest:hamcrest:2.2")
	testImplementation("com.h2database:h2:1.4.200")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.5.21")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

	testImplementation("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
	testImplementation("com.github.seeseemelk:MockBukkit-v1.17:1.7.0")
}

//  TODO: Add "useJUnitPlatform" to make tests work