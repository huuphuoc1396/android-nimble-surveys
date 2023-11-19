plugins {
    id(Plugins.ANDROID_APP)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.HILT_ANDROID)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
}

android {
    namespace = Configs.NAMSPACE
    compileSdk = Configs.COMPLIE_SDK

    defaultConfig {
        applicationId = Configs.APP_ID
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK
        versionCode = Configs.VERSION_CODE
        versionName = Configs.VERSION_NAME

        testInstrumentationRunner = Configs.ANDROID_JUNIT_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName(Builds.Debug.name) {
            storeFile = file("../debug.keystore")
            storePassword = "qv1HnZCp"
            keyAlias = "nimble-surveys"
            keyPassword = "qv1HnZCp"
        }
    }

    buildTypes {
        getByName(Builds.Release.name) {
            isMinifyEnabled = Builds.Release.isMinifyEnabled
            isShrinkResources = Builds.Release.isShrinkResources
            signingConfig = signingConfigs[Builds.Debug.name]
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }

        getByName(Builds.Debug.name) {
            isMinifyEnabled = Builds.Debug.isMinifyEnabled
            isShrinkResources = Builds.Debug.isShrinkResources
            signingConfig = signingConfigs[Builds.Debug.name]
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }
    }

    flavorDimensions += Builds.SHARED_DIMENSION
    productFlavors {
        create(Builds.Flavors.DEV) {
            applicationIdSuffix = ".${Builds.Flavors.DEV}"
        }

        create(Builds.Flavors.PROD) {}
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
        freeCompilerArgs = listOf(
            "-Xcontext-receivers",
            "-Xstring-concat=inline",
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(Libs.AndroidX.LIFECYCLE_RUNTIME_COMPOSE)
    implementation(Libs.AndroidX.ACTIVITY_COMPOSE)

    implementation(Libs.Hilt.ANDROID)
    kapt(Libs.Hilt.COMPILER)

    implementation(Libs.Kotlin.COROUTINES_ANDROID)

    implementation(platform(Libs.AndroidX.COMPOSE_BOM))
    implementation(Libs.AndroidX.COMPOSE_UI)
    implementation(Libs.AndroidX.COMPOSE_UI_GRAPHICS)
    implementation(Libs.AndroidX.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Libs.AndroidX.COMPOSE_MATERIAL)

    implementation(Libs.Accompanist.INSETS)
    implementation(Libs.Accompanist.PAGER)
    implementation(Libs.Accompanist.SWIPE_REFRESH)
    implementation(Libs.Accompanist.SYSTEM_UI)

    implementation(Libs.Hilt.ANDROID)
    implementation(Libs.Hilt.NAV_COMPOSE)
    kapt(Libs.Hilt.COMPILER)

    implementation(Libs.TIMBER)

    debugImplementation(Libs.AndroidX.COMPOSE_UI_TOOLING)
    debugImplementation(Libs.AndroidX.COMPOSE_UI_TEST_MANIFEST)

    testImplementation(Libs.JUNIT)

    androidTestImplementation(platform(Libs.AndroidX.COMPOSE_BOM))
    androidTestImplementation(Libs.AndroidX.COMPOSE_UI_TEST_JUNIT4)

    androidTestImplementation(Libs.AndroidX.TEST_JUNIT)
    androidTestImplementation(Libs.AndroidX.TEST_ESPRESSO_CORE)

    implementation(platform(Libs.Firebase.BOM))
    implementation(Libs.Firebase.ANALYTICS)
    implementation(Libs.Firebase.CRASHLYTICS)
}