// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APP) version Versions.GRADLE apply false
    id(Plugins.ANDROID_LIB) version Versions.GRADLE apply false

    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.KOTLIN_JVM) version Versions.KOTLIN apply false

    id(Plugins.HILT_ANDROID) version Versions.HILT apply false

    id(Plugins.GOOGLE_SERVICES) version Versions.GOOGLE_SERVICES apply false
    id(Plugins.FIREBASE_CRASHLYTICS) version Versions.FIREBASE_CRASHLYTICS apply false

    id(Plugins.PROTOBUF) version Versions.PROTOBUF apply false
    id(Plugins.KOVER) version Versions.KOVER apply false
    id(Plugins.ANDROID_JUNIT5) version Versions.ANDROID_JUNIT5_VERSION apply false

    id(Plugins.DETEKT) version Versions.DETEKT apply false

    id(Plugins.KTLINT) version Versions.KTLINT apply false
}
