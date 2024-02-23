plugins {
    id(Plugins.JAVA_LIB)
    id(Plugins.KOTLIN_JVM)
}

dependencies{
    compileOnly(Libs.Detekt.DETEKT_API)
}