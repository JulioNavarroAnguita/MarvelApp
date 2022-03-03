plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlin)
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutinesCore)
    implementation(Libraries.kotlinCoroutinesAndroid)
    // 3rd party libraries
    implementation(Libraries.koinCore)
    api(Libraries.arrowCore)
    api(Libraries.arrowSyntax)
    // testing dependencies - Unit Test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoKotlin)
    testImplementation(Libraries.kotlinCoroutinesTest)
    // koin testing tools
    testImplementation(Libraries.koinTest)
}
