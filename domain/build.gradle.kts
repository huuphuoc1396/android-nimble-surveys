plugins {
    id(Plugins.JAVA_LIB)
    id(Plugins.KOTLIN_JVM)
    id(Plugins.KOVER)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(Libs.Kotlin.COROUTINES_CORE)
    implementation(Libs.JAVAX_INJECT)

    testImplementation(Libs.JUNIT)
    testImplementation(Libs.MOCKK)
    testImplementation(Libs.KOTEST)
    testImplementation(Libs.Kotlin.COROUTINES_TEST)
}

koverReport {
    filters {
        excludes {
            // Add class or package names to exclude from coverage report
        }
    }
    verify {
        rule {
            isEnabled = true
        }
    }
}