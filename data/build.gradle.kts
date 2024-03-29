import com.google.protobuf.gradle.id

plugins {
    id(Plugins.ANDROID_LIB)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.PROTOBUF)
    id(Plugins.KOVER)
    id(Plugins.ANDROID_JUNIT5)
}

android {
    namespace = "${Configs.NAMSPACE}.data"
    compileSdk = Configs.COMPLIE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK

        testInstrumentationRunner = Configs.ANDROID_JUNIT_RUNNER
    }

    buildTypes {
        getByName(Builds.Release.name) {
            isMinifyEnabled = Builds.Release.isMinifyEnabled
            proguardFiles(getDefaultProguardFile(Configs.PROGUARD_FILE), Configs.PROGUARD_RULES)
        }

        getByName(Builds.Debug.name) {
            isMinifyEnabled = Builds.Debug.isMinifyEnabled
        }
    }

    flavorDimensions += Builds.SHARED_DIMENSION
    productFlavors {
        create(Builds.Flavors.DEV) {
            buildConfigField("String", "BASE_API_URL", "\"https://survey-api-staging.nimblehq.co\"")
        }

        create(Builds.Flavors.PROD) {
            buildConfigField("String", "BASE_API_URL", "\"https://survey-api.nimblehq.co\"")
        }
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

    testOptions {
        unitTests.all { it.useJUnitPlatform() }
    }

    externalNativeBuild {
        cmake {
            path = File("cpp/CMakeLists.txt")
        }
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    implementation(project(Modules.DOMAIN))

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.SECURITY_CRYPTO)

    implementation(Libs.Hilt.ANDROID)
    kapt(Libs.Hilt.COMPILER)

    implementation(Libs.Kotlin.COROUTINES_ANDROID)

    implementation(Libs.Retrofit.RETROFIT)
    implementation(Libs.Retrofit.RETROFIT_MOSHI)

    implementation(Libs.Moshi.MOSHI)
    implementation(Libs.Moshi.MOSHI_ADAPTER)
    implementation(Libs.Moshi.MOSHI_KOTLIN)

    implementation(Libs.TIMBER)

    implementation(Libs.CHUCKER)
    implementation(Libs.LOGGING_INTERCEPTOR)

    testImplementation(platform(Libs.JUnit5.BOM))
    testImplementation(Libs.JUnit5.JUPITER)
    testImplementation(Libs.MOCKK)
    testImplementation(Libs.KOTEST)
    testImplementation(Libs.Kotlin.COROUTINES_TEST)

    androidTestImplementation(Libs.KOTEST)
    androidTestImplementation(Libs.TURBINE)
    androidTestImplementation(Libs.MOCKK_ANDROID)
    androidTestImplementation(Libs.Kotlin.COROUTINES_TEST)
    androidTestImplementation(Libs.AndroidX.TEST_JUNIT)
    androidTestImplementation(Libs.AndroidX.TEST_CORE_KTX)
    androidTestImplementation(Libs.AndroidX.TEST_ESPRESSO_CORE)

    implementation(Libs.Datastore.SECURITY_DATASTORE)
    implementation(Libs.Datastore.SECURITY_DATASTORE_PREFERENCES)
    implementation(Libs.Protobuf.JAVALITE)

    implementation(Libs.Room.ROOM_CORE)
    kapt(Libs.Room.ROOM_COMPILER)
    implementation(Libs.Room.ROOM_PAGING)
    implementation(Libs.Room.ROOM_RUNTIME)

    implementation(Libs.Sql.SQL_CIPHER)
    implementation(Libs.Sql.SQL_CORE)
}

protobuf {
    protoc {
        artifact = "${Libs.Protobuf.PROTOC}:osx-x86_64"
    }

    plugins {
        id("javalite") {
            artifact = "${Libs.Protobuf.JAVALITE}:osx-x86_64"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

koverReport {
    filters {
        excludes {
            // Add class or package names to exclude from coverage report
            packages(Excludes.excludesPackages)
        }
    }
    verify {
        rule {
            isEnabled = true
        }
    }
}