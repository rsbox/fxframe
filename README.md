# FXFrame
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/rsbox/fxframe/release)
![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/rsbox/fxframe?include_prereleases)
![Bintray](https://img.shields.io/bintray/v/rsbox/fxframe/fxframe)

A TornadoFX extension framework which allows a simple construction of custom
borderless JavaFX stages which behave like native windows.

## Gradle
Add the following repository to your Gradle build file to add the RSBox 
GitHub maven repository.

```kotlin
repositories {
    maven(url = "https://dl.bintray.com/rsbox/fxframe")
}
```

Once the repository has been accepted into **Maven Central** and or **JCenter**. The following is the
only thing required.

```kotlin
repositories {
    jcenter()
    // or
    mavenCentral()
}
```


Add the following dependency. **NOTE:** You must also have JavaFX and TornadoFX
 dependencies added.
 
```kotlin
dependencies {
    implementation("io.rsbox:fxframe:1.1.0")
}
```

## Usage
In order to create a JavaFX application, create an app class similar to the
example defined below.

```kotlin
class ExampleApp : FXFrameApp() {

    override val skin = FXFrameSkin.ARCDARK

    override val view = find<MyPrimaryView>()
   
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch<ExampleApp>()
        }           
    }
}
```

## Future Plans

