# FXFrame
![release](https://github.com/rsbox/fxframe/workflows/release/badge.svg?event=release)

A TornadoFX extension framework which allows a simple construction of custom
borderless JavaFX stages which behave like native windows.

## Gradle
Add the following repository to your Gradle build file to add the RSBox 
GitHub maven repository.

```kotlin
repositories {
    maven(url = "https://maven.pkg.github.com/rsbox/fxframe")
}
```

Add the following dependency. **NOTE:** You must also have JavaFX and TornadoFX
 dependencies added.
 
```kotlin
dependencies {
    implementation("io.rsbox.fxframe:fxframe:1.0.2")
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
