plugins {
    id(Plugins.ANDROID_LIB)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
}

android {
    namespace = "${Configs.NAMSPACE}.data"
    compileSdk = Configs.COMPLIE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK

        testInstrumentationRunner = Configs.ANDROID_JUNIT_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = Builds.Release.isMinifyEnabled
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }

        debug {
            isMinifyEnabled = Builds.Debug.isMinifyEnabled
        }
    }

    flavorDimensions += Builds.SHARED_DIMENSION
    productFlavors {
        create(Builds.Flavors.DEV) {}

        create(Builds.Flavors.PROD) {}
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
        freeCompilerArgs = listOf(
            "-Xstring-concat=inline",
        )
    }
}

dependencies {

    implementation(project(Modules.DOMAIN))

    implementation(Libs.AndroidX.CORE_KTX)

    implementation(Libs.Hilt.ANDROID)
    kapt(Libs.Hilt.COMPILER)

    implementation(Libs.Kotlin.COROUTINES_ANDROID)

    implementation(Libs.Retrofit.RETROFIT)
    implementation(Libs.Retrofit.RETROFIT_MOSHI)

    implementation(Libs.Moshi.MOSHI)
    implementation(Libs.Moshi.MOSHI_ADAPTER)
    implementation(Libs.Moshi.MOSHI_KOTLIN)

    implementation(Libs.TIMBER)

    implementation(Libs.LOGGING_INTERCEPTOR)

    debugImplementation(Libs.Chucker.LIB)
    releaseImplementation(Libs.Chucker.LIB_NO_OP)

    testImplementation(Libs.JUNIT)

    androidTestImplementation(Libs.AndroidX.TEST_JUNIT)
}