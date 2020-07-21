plugins {
    kotlin("jvm") version Project.kotlinVersion
    id("org.openjfx.javafxplugin") version Plugin.openjfx
    id("com.jfrog.bintray") version Plugin.bintray
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

    group = "io.rsbox"
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

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.rsbox"
            artifactId = "fxframe"
            version = Project.version

            from(components["java"])
            artifact(sourcesJar)
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USERNAME")
    key = System.getenv("BINTRAY_TOKEN")
    publish = true

    setPublications("maven")

    pkg.apply {
        repo = "fxframe"
        name = "fxframe"
        userOrg = "rsbox"
        description = "A TornadoFX Borderless Window Extension Library"
        setLabels("kotlin", "javafx", "tornadofx")
        setLicenses("MIT")
        version.apply {
            name = Project.version
        }
    }
}