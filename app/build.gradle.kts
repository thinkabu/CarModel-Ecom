plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.ltdd_dacs3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ltdd_dacs3"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //Navigation component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.2")

    //loading button
    implementation ("br.com.simplepass:loading-button-android:2.2.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")

    //Android Ktx
    implementation ("androidx.navigation:navigation-fragment-ktx:2.4.2")

    //circular image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //viewpager2 indicatior
    implementation ("io.github.vejei.viewpagerindicator:viewpagerindicator:1.0.0-alpha.1")
    implementation("com.tbuonomo:dotsindicator:5.0")

    //stepView
    implementation ("com.shuhart.stepview:stepview:1.5.1")

    //Dagger hilt
    implementation ("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    //Firebase
    implementation ("com.google.firebase:firebase-auth:21.0.6")

    //Coroutines with firebase
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.1")

    implementation ("androidx.fragment:fragment:1.3.4") // hoặc phiên bản mới nhất

}