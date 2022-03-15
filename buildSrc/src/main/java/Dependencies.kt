object Dependencies {

    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:7.1.2"

    object KOTLIN {
        private const val VERSION = "1.6.10"
        const val REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$VERSION"
        const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
    }

    private const val CORE_KTX_VERSION = "1.7.0"
    const val CORE_KTX = "androidx.core:core-ktx:$CORE_KTX_VERSION"

    private const val APPCOMPAT_VERSION = "1.4.1"
    const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"

    private const val MULTIDEX_VERSION = "1.0.3"
    const val MULTIDEX = "com.android.support:multidex:$MULTIDEX_VERSION"

    private const val GOOGLE_PLAY_SERVICE_VERSION = "20.1.0"
    const val GOOGLE_PLAY_SERVICE =
        "com.google.android.gms:play-services-auth:$GOOGLE_PLAY_SERVICE_VERSION"

    object LIFECYCLE {
        private const val VERSION = "2.4.1"
        const val REACTIVESTREAMS_KTX =
            "androidx.lifecycle:lifecycle-reactivestreams-ktx:$VERSION"
        const val VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:$VERSION"
    }

    private const val MATERIAL_VERSION = "1.5.0"
    const val MATERIAL = "com.google.android.material:material:$MATERIAL_VERSION"

    object NAVIGATION {
        private const val VERSION = "2.4.1"
        const val FRAGMENT =
            "androidx.navigation:navigation-fragment-ktx:$VERSION"
        const val UI = "androidx.navigation:navigation-ui-ktx:$VERSION"
        const val COMPOSE = "androidx.navigation:navigation-compose:$VERSION"
        const val SAFE_ARGS_GRADLE_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:$VERSION"
    }

    private const val CONSTRAINT_LAYOUT_VERSION = "2.1.3"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"

    object GLIDE {
        private const val VERSION = "4.11.0"
        const val GLIDE = "com.github.bumptech.glide:glide:$VERSION"
        const val COMPILER = "com.github.bumptech.glide:compiler:$VERSION"
    }

    object COMPOSE {
        const val VERSION = "1.1.1"
        const val UI_UI = "androidx.compose.ui:ui:$VERSION"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$VERSION"
        const val UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4:$VERSION"
        const val FOUNDATION = "androidx.compose.foundation:foundation:$VERSION"
        const val MATERIAL = "androidx.compose.material:material:$VERSION"
        const val MATERIAL_ICONS_CORE = "androidx.compose.material:material-icons-core:$VERSION"
        const val MATERIAL_ICONS_EXTENDED =
            "androidx.compose.material:material-icons-extended:$VERSION"
        const val RUNTIME_LIVEDATA = "androidx.compose.runtime:runtime-livedata:$VERSION"
        const val RUNTIME_RXJAVA2 ="androidx.compose.runtime:runtime-rxjava2:$VERSION"
    }

    private const val ACTIVITY_VERSION = "1.4.0"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$ACTIVITY_VERSION"



    private const val RXJAVA3_RXJAVA_VERSION = "3.1.3"
    const val RXJAVA3_RXJAVA = "io.reactivex.rxjava3:rxjava:$RXJAVA3_RXJAVA_VERSION"

    private const val RXJAVA3_RXANDROID_VERSION = "3.0.0"
    const val RXJAVA3_RXANDROID = "io.reactivex.rxjava3:rxandroid:$RXJAVA3_RXANDROID_VERSION"

    private const val RXJAVA3_RXKOTLIN_VERSION = "3.0.1"
    const val RXJAVA3_RXKOTLIN = "io.reactivex.rxjava3:rxkotlin:$RXJAVA3_RXKOTLIN_VERSION"

    private const val RXJAVA3_RXRELAY_VERSION = "3.0.1"
    const val RXJAVA_RXRELAY = "com.jakewharton.rxrelay3:rxrelay:$RXJAVA3_RXRELAY_VERSION"

    private const val RXJAVA3_RXBINDING_VERSION = "4.0.0"
    const val RXJAVA3_RXBINDING = "com.jakewharton.rxbinding4:rxbinding:$RXJAVA3_RXBINDING_VERSION"

    object KOIN {
        private const val VERSION = "3.1.5"
        const val CORE = "io.insert-koin:koin-core:$VERSION"
        const val TEST = "io.insert-koin:koin-test:$VERSION"
        const val TEST_JUNIT4 = "io.insert-koin:koin-test-junit4:$VERSION"
        const val TEST_JUNIT5 = "io.insert-koin:koin-test-junit5:$VERSION"
        const val ANDROID = "io.insert-koin:koin-android:$VERSION"
        const val ANDROID_COMPAT = "io.insert-koin:koin-android-compat:$VERSION"
        const val ANDROIDX_WORKMANAGER = "io.insert-koin:koin-androidx-workmanager:$VERSION"
        const val ANDROIDX_NAVIGATION = "io.insert-koin:koin-androidx-navigation:$VERSION"
        const val ANDROIDX_COMPOSE = "io.insert-koin:koin-androidx-compose:$VERSION"
    }

    private const val THREETEN_VERSION = "1.3.1"
    const val THREETEN = "com.jakewharton.threetenabp:threetenabp:$THREETEN_VERSION"

    private const val JUNIT_VERSION = "4.13.2"
    const val JUNIT = "junit:junit:$JUNIT_VERSION"

    private const val TEST_EXT_JUNIT_VERSION = "1.1.3"
    const val TEST_EXT_JUNIT = "androidx.test.ext:junit:$TEST_EXT_JUNIT_VERSION"

    private const val TEST_ESPRESSO_VERSION= "3.4.0"
    const val TEST_ESPRESSO = "androidx.test.espresso:espresso-core:$TEST_ESPRESSO_VERSION"

    private const val MOCKITO_VERSION = "2.2.0"
    const val MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:$MOCKITO_VERSION"

    private const val TRUTH_VERSION = "1.1"
    const val TRUTH = "com.google.truth:truth:$TRUTH_VERSION"

    private const val ARCH_CORE_TESTING_VERSION = "2.1.0"
    const val ARCH_CORE_TESTING = "androidx.arch.core:core-testing:$ARCH_CORE_TESTING_VERSION"

    object SQLDELIGHT{
        private const val VERSION = "1.5.3"
        const val GRADLE_PLUGIN ="com.squareup.sqldelight:gradle-plugin:$VERSION"
    }
}