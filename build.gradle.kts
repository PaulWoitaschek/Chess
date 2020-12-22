import org.jetbrains.compose.compose

plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.compose") version "0.3.0-build135"
    id("com.github.ben-manes.versions") version "0.36.0"
}

repositories {
    jcenter()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "Main"
    }
}
