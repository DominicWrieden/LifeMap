import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    kotlin("android")
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    val properties = Properties().apply {
        val propertiesFile = File("${project.rootDir}/local.properties")

        if (propertiesFile.exists()) {
            load(FileInputStream(propertiesFile))
        }
    }

    flavorDimensions.add("server")

    productFlavors {
        create("oldAPI") {
            dimension = "server"
            buildConfigField("String", "API_BASE_URL", properties.getProperty("API_BASE_URL_OLD"))
        }

        create("newAPI") {
            buildConfigField("String", "API_BASE_URL", "")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.RXJAVA3_RXJAVA)
    implementation(Dependencies.RXJAVA3_RXANDROID)
    implementation(Dependencies.RXJAVA3_RXKOTLIN)
    implementation(Dependencies.RXJAVA3_RXRELAY)
    implementation(Dependencies.MOSHI)
    implementation(Dependencies.RETROFIT.RETROFIT)
    implementation(Dependencies.RXJAVA3_RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT.CONVERTER_MOSHI)
    implementation(Dependencies.KOIN.CORE)
    implementation(Dependencies.KOIN.ANDROID)
    implementation(Dependencies.KOIN.TEST)
    implementation(Dependencies.THREETEN)
    implementation(Dependencies.SECURITY_CRYPTO)
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.TEST_EXT_JUNIT)
}
