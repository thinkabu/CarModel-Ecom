buildscript {
    dependencies {
//        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40.1")
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
