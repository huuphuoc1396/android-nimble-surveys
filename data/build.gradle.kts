plugins {
    id(Plugins.ANDROID_LIB)
    id(Plugins.KOTLIN_ANDROID)
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
            isMinifyEnabled = BuildTypes.Release.isMinifyEnabled
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }

        debug {
            isMinifyEnabled = BuildTypes.Debug.isMinifyEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_17}"
    }
}

dependencies {

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.TIMBER)

    testImplementation(Libs.JUNIT)

    androidTestImplementation(Libs.AndroidX.TEST_JUNIT)
}