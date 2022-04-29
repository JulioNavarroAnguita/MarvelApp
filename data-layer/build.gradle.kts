plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    kotlin(Plugins.kotlinKapt)
}

android {
    compileSdk = AndroidSdk.compile

    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = Libraries.testRunner
    }

    sourceSets {
        getByName("main") { java.srcDir("src/main/kotlin") }
        getByName("test") { java.srcDir("src/test/kotlin") }
        getByName("androidTest") { java.srcDir("src/androidTest/kotlin") }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutinesCore)
    implementation(Libraries.retrofitCoroutinesAdapter)

    // other modules
    implementation(project(":domain-layer"))

    // 3rd party libraries
    implementation(Libraries.koinAndroid)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitMoshiConverter)
    implementation(Libraries.moshi)
    implementation(Libraries.moshiKotlin)
    implementation(Libraries.interceptor)
    kapt(Libraries.moshiKotlinCodegen)
    implementation(Libraries.roomKtx)
    kapt(Libraries.roomCompiler)

    // shared preferences
    implementation(Libraries.encryptSharedPreferences)
    // testing dependencies - Unit Test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.kotlinCoroutinesTest)

    // koin testing tools
    testImplementation(Libraries.koinTest)

    // testing dependencies - Instrumentation Test
    androidTestImplementation(Libraries.testRunner)
    androidTestImplementation(Libraries.testRules)
    testImplementation(Libraries.mockk)


    // koin testing tools
    androidTestImplementation(Libraries.koinTest) {
        exclude("group", "org.mockito")
        exclude("group", "mockito-inline")
    }
}
