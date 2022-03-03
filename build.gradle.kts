// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath(Build.androidGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.navigationSafeArgs)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    }
}

/*allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
        maven("https://jitpack.io")
    }
}*/

tasks.register("clean").configure {
    delete("build")
}
