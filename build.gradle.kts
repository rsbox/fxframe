plugins {
    kotlin("jvm") version Project.kotlinVersion
    id("org.openjfx.javafxplugin") version Plugin.openjfx
    `maven-publish`
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

    group = "io.rsbox.fxframe"
    version = Project.version

    repositories {
        mavenLocal()
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

@Suppress("DEPRECATION")
val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "io.rsbox.fxframe"
            artifactId = "framefx"
            version = Project.version
            from(components["java"])
            artifact(sourcesJar.get())
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/rsbox/fxframe")

                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
