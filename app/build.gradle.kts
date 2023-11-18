plugins {
    id(Plugins.ANDROID_APP)
    id(Plugins.KOTLIN_ANDROID)
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

    buildTypes {
        release {
            isMinifyEnabled = BuildTypes.Release.isMinifyEnabled
            isShrinkResources = BuildTypes.Release.isShrinkResources
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }

        debug {
            isMinifyEnabled = BuildTypes.Debug.isMinifyEnabled
            isShrinkResources = BuildTypes.Debug.isShrinkResources
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Configs.VERSION_COMPOSE_COMPILER
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(Libs.AndroidX.ACTIVITY_COMPOSE)

    implementation(platform(Libs.AndroidX.COMPOSE_BOM))
    implementation(Libs.AndroidX.COMPOSE_UI)
    implementation(Libs.AndroidX.COMPOSE_UI_GRAPHICS)
    implementation(Libs.AndroidX.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Libs.AndroidX.COMPOSE_MATERIAL3)

    debugImplementation(Libs.AndroidX.COMPOSE_UI_TOOLING)
    debugImplementation(Libs.AndroidX.COMPOSE_UI_TEST_MANIFEST)

    testImplementation(Libs.JUNIT)

    androidTestImplementation(platform(Libs.AndroidX.COMPOSE_BOM))
    androidTestImplementation(Libs.AndroidX.COMPOSE_UI_TEST_JUNIT4)

    androidTestImplementation(Libs.AndroidX.TEST_JUNIT)
    androidTestImplementation(Libs.AndroidX.TEST_ESPRESSO_CORE)
}