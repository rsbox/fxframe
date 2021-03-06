import org.gradle.api.JavaVersion

object Project {
    const val version = "1.2.0"
    const val kotlinVersion = "1.3.72"
    const val gradleVersion = "6.3"
    val jvmVersion = JavaVersion.VERSION_11
}

object Plugin {
    const val openjfx = "0.0.9"
    const val bintray = "1.8.5"
}

object Library {
    private object Version {
        const val tornadofx = "1.7.20"
    }

    const val tornadofx = "no.tornado:tornadofx:${Version.tornadofx}"
}