import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = AppConfig.TARGET_SDK_VERSION

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        /* Migrate to Kotlin DSL if needed
        ndk { // TODO 2022-02-25: Kick out ndk if not needed anymore. See arc gis
            abiFilters "armeabi", "armeabi-v7a", "x86", "mips"
        }
         */
    }
    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        dataBinding = true
    }

    //TODO löschen wenn arc gis gelöscht wird
    /* Migrate to Kotlin DSL if needed
    packagingOptions {
        exclude 'META-INF/LGPL2.1'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/NOTICE'
    }
     */

    val properties = Properties().apply{
        val propertiesFile = File("${project.rootDir}/local.properties")

        if (propertiesFile.exists()) {
            load(FileInputStream(propertiesFile))
        }
    }


    flavorDimensions.add("server")

    productFlavors {
        create("oldAPI") {
            dimension = "server"
            buildConfigField("String", "ARCGIS_API_KEY", properties.getProperty("ARCGIS_API_KEY"))
        }

        create("newAPI") {
            dimension = "server"
            buildConfigField("String", "ARCGIS_API_KEY", properties.getProperty("ARCGIS_API_KEY"))
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.COMPOSE.VERSION
    }

}

dependencies {
    implementation(project(":data"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN.REFLECT) // TODO really neccessary?
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MULTIDEX) //TODO really neccessary?
    implementation(Dependencies.GOOGLE_PLAY_SERVICE)
    // TODO 2022-02-25: Delete? implementation "androidx.lifecycle:lifecycle-extensions-ktx:$androidx_lifecycle_version"
    implementation(Dependencies.LIFECYCLE.REACTIVESTREAMS_KTX)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.NAVIGATION.FRAGMENT)
    implementation(Dependencies.NAVIGATION.UI)
    implementation(Dependencies.NAVIGATION.COMPOSE)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.GLIDE.GLIDE)
    kapt(Dependencies.GLIDE.COMPILER)
    implementation(Dependencies.COMPOSE.UI_UI)
    implementation(Dependencies.COMPOSE.UI_TOOLING)
    implementation(Dependencies.COMPOSE.FOUNDATION)
    implementation(Dependencies.COMPOSE.MATERIAL)
    implementation(Dependencies.COMPOSE.MATERIAL_ICONS_CORE)
    implementation(Dependencies.COMPOSE.MATERIAL_ICONS_EXTENDED)
    implementation(Dependencies.ACTIVITY_COMPOSE)
    implementation(Dependencies.LIFECYCLE.VIEWMODEL_COMPOSE)
    implementation(Dependencies.COMPOSE.RUNTIME_LIVEDATA)
    implementation(Dependencies.COMPOSE.RUNTIME_RXJAVA2)
    androidTestImplementation(Dependencies.COMPOSE.UI_TEST_JUNIT4)
    implementation(Dependencies.RXJAVA3_RXJAVA)
    implementation(Dependencies.RXJAVA3_RXANDROID)
    implementation(Dependencies.RXJAVA3_RXKOTLIN)
    implementation(Dependencies.RXJAVA3_RXRELAY)
    implementation(Dependencies.RXJAVA3_RXBINDING)
    implementation(Dependencies.KOIN.CORE)
    testImplementation(Dependencies.KOIN.TEST)
    testImplementation(Dependencies.KOIN.TEST_JUNIT4)
    testImplementation(Dependencies.KOIN.TEST_JUNIT5)
    implementation(Dependencies.KOIN.ANDROID)
    implementation(Dependencies.KOIN.ANDROID_COMPAT)
    implementation(Dependencies.KOIN.ANDROIDX_WORKMANAGER)
    implementation(Dependencies.KOIN.ANDROIDX_NAVIGATION)
    implementation(Dependencies.KOIN.ANDROIDX_COMPOSE)
    implementation(Dependencies.THREETEN)
    //TODO 2022.02.25: ArcGis needs to be fixed. Best case: Import old API like this https://developers.arcgis.com/android/get-started/
    // implementation 'com.esri.arcgis.android:arcgis-android:10.2.8-1'
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.TEST_EXT_JUNIT)
    androidTestImplementation(Dependencies.TEST_ESPRESSO)
    testImplementation(Dependencies.MOCKITO)
    testImplementation(Dependencies.TRUTH)
    androidTestImplementation(Dependencies.ARCH_CORE_TESTING)
}
