

```
build.gradle(project level)

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

def kotlin_extensions_version = "0.0.5"
implementation "com.github.theakashdev:KotlinExtensions:$kotlin_extensions_version"

```
