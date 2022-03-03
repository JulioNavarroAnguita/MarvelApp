plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
}

android {
    compileSdk = AndroidSdk.compile
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main") { java.srcDir("src/main/kotlin") }
    }

    lintOptions.isAbortOnError = false

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    // other modules
    implementation(project(":presentation-layer"))
    implementation(project(":domain-layer"))
    implementation(project(":data-layer"))

    // 3rd party libraries
    implementation(Libraries.koinAndroid)
}
