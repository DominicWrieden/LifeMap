// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    apply(from="versions.gradle")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.ANDROID_GRADLE_PLUGIN)
        classpath(Dependencies.KOTLIN.GRADLE_PLUGIN)
        classpath(Dependencies.SQLDELIGHT.GRADLE_PLUGIN)
        classpath(Dependencies.NAVIGATION.SAFE_ARGS_GRADLE_PLUGIN)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        //maven { url 'https://esri.bintray.com/arcgis'}
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

