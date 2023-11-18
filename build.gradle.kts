// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APP) version Configs.VERSION_GRADLE apply false
    id(Plugins.ANDROID_LIB) version Configs.VERSION_GRADLE apply false

    id(Plugins.KOTLIN_ANDROID) version Configs.VERSION_KOTLIN apply false
    id(Plugins.KOTLIN_JVM) version Configs.VERSION_KOTLIN apply false
}