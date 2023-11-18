plugins {
    id(Plugins.JAVA_LIB)
    id(Plugins.KOTLIN_JVM)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(Libs.Kotlin.COROUTINES_CORE)
    implementation(Libs.JAVAX_INJECT)

    testImplementation(Libs.JUNIT)
}