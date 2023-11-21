object Libs {

    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME_KTX}"
        const val LIFECYCLE_RUNTIME_COMPOSE = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE_RUNTIME_KTX}"
        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
        const val SECURITY_CRYPTO = "androidx.security:security-crypto:${Versions.SECURITY_CRYPTO}"

        const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
        const val COMPOSE_UI = "androidx.compose.ui:ui"
        const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val COMPOSE_UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material"
        const val NAV_COMPOSE = "androidx.navigation:navigation-compose:${Versions.NAV_COMPOSE}"

        const val TEST_JUNIT = "androidx.test.ext:junit:${Versions.TEST_JUNIT}"
        const val TEST_ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.TEST_ESPRESSO_CORE}"

        const val PAGGING_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGGING_RUNTIME}"
        const val PAGGING_COMPOSE = "androidx.paging:paging-compose:${Versions.PAGGING_COMPOSE}"
    }

    object Kotlin {
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    }

    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

    const val JUNIT = "junit:junit:${Versions.JUNIT}"

    const val JAVAX_INJECT = "javax.inject:javax.inject:${Versions.JAVAX_INJECT}"

    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
        const val NAV_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAV_COMPOSE}"
    }

    object Moshi {
        const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
        const val MOSHI_ADAPTER = "com.squareup.moshi:moshi-adapters:${Versions.MOSHI}"
        const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
    }

    object Retrofit {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    }

    object Chucker {
        const val LIB = "com.github.chuckerteam.chucker:library:${Versions.CHUCKER}"
        const val LIB_NO_OP = "com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER}"
    }

    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"

    object Accompanist {
        const val INSETS = "com.google.accompanist:accompanist-insets:${Versions.ACCOMPANIST}"
        const val PAGER = "com.google.accompanist:accompanist-pager:${Versions.ACCOMPANIST}"
        const val SWIPE_REFRESH = "com.google.accompanist:accompanist-swiperefresh:${Versions.ACCOMPANIST}"
        const val SYSTEM_UI = "com.google.accompanist:accompanist-systemuicontroller:${Versions.ACCOMPANIST}"
    }

    object Firebase {
        const val BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
        const val ANALYTICS = "com.google.firebase:firebase-analytics"
        const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
    }

    object Datastore{
       const val SECURITY_DATASTORE = "io.github.osipxd:security-crypto-datastore:${Versions.SECURITY_DATASTORE}"
       const val SECURITY_DATASTORE_PREFERENCES = "io.github.osipxd:security-crypto-datastore-preferences:${Versions.SECURITY_DATASTORE}"
    }

    const val COIL_COMPOSE = "io.coil-kt:coil-compose:${Versions.COIL}"
}