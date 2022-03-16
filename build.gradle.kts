// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {

    apply(from="versions.gradle")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // for gradle plugin to check newer dependency versions
    }
    dependencies {
        classpath(Dependencies.ANDROID_GRADLE_PLUGIN)
        classpath(Dependencies.KOTLIN.GRADLE_PLUGIN)
        classpath(Dependencies.SQLDELIGHT.GRADLE_PLUGIN)
        classpath(Dependencies.NAVIGATION.SAFE_ARGS_GRADLE_PLUGIN)
        classpath(Dependencies.DEPENDENCY_UPDATE_WATCHER_GRADLE_PLUGIN)
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

// Following code is for checking newer dependency versions
// see https://github.com/ben-manes/gradle-versions-plugin
apply(plugin = "com.github.ben-manes.versions")

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
tasks.withType<DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
}
