

```build.gradle(project level)
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

kotlin_extensions_version = '0.0.1'
implementation "com.github.theakashdev:KotlinExtensions:$kotlin_extensions_version"

```
