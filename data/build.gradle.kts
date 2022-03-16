plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.squareup.sqldelight")
}

android {
    compileSdk = AppConfig.TARGET_SDK_VERSION

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }


    flavorDimensions.add("server")

    productFlavors {
        create("oldAPI") {
            dimension = "server"
        }

        create("newAPI") {
            dimension = "server"
        }
    }


}

dependencies {
    api(project(":api"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.RXJAVA3_RXJAVA)
    implementation(Dependencies.RXJAVA3_RXKOTLIN)
    implementation(Dependencies.SQLDELIGHT.ANDROID_DRIVER)
    testImplementation(Dependencies.SQLDELIGHT.SQLITE_DRIVER)
    implementation(Dependencies.SQLDELIGHT.RXJAVA3_EXTENSION)
    implementation(Dependencies.KOIN.CORE)
    implementation(Dependencies.KOIN.ANDROID)
    testImplementation(Dependencies.KOIN.TEST)
    implementation(Dependencies.THREETEN)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.TEST_EXT_JUNIT)
}
