plugins {
    kotlin("jvm") version Project.kotlinVersion
    id("org.openjfx.javafxplugin") version Plugin.openjfx
}

tasks.withType<Wrapper> {
    gradleVersion = Project.gradleVersion
}

javafx {
    version = "11"
    modules = listOf("javafx.base", "javafx.controls", "javafx.graphics", "javafx.swing", "javafx.web")
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "org.framefx"
    version = Project.version

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        implementation(Library.tornadofx)
    }

    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = Project.jvmVersion.toString()
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = Project.jvmVersion.toString()
        }
    }
}