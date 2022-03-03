
plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.safeArgs)
    id(Plugins.kotlinAndroid)
}

android {
    compileSdk = AndroidSdk.compile
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
        dataBinding = true
    }

    lint {
        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
    }
}

dependencies {

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelKtx)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.recyclerview)
    implementation(Libraries.cardview)
    implementation(Libraries.googleMaterial)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationUiKtx)
    // other modules
    implementation(project(":domain-layer"))
    // Compose
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.3.1")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.1.0-alpha06")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01")
    implementation("io.coil-kt:coil-compose:1.3.2")
    // Animations
    implementation("androidx.compose.animation:animation:1.1.0-alpha06")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.0-alpha06")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-rc01")
    // When using a MDC theme
    implementation("com.google.android.material:compose-theme-adapter:1.0.4")
    implementation("androidx.compose.material:material-icons-extended:1.0.4")
    // 3rd party libraries
    implementation(Libraries.koinAndroid)
    implementation(Libraries.glide)
    // testing dependencies - Unit Test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoKotlin)
    testImplementation(Libraries.kotlinCoroutinesTest)

    // koin testing tools
    testImplementation(Libraries.koinTest)

    // testing dependencies - Instrumentation Test
    androidTestImplementation(Libraries.mockitoAndroid)
    androidTestImplementation(Libraries.testRunner)
    androidTestImplementation(Libraries.testRules)

    // koin testing tools
    androidTestImplementation(Libraries.koinTest) {
        exclude("group", "org.mockito")
        exclude("group", "mockito-inline")
    }
}
