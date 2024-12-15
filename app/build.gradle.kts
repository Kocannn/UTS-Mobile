plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.uts"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("E:\\Android-Studio\\Praktikum\\UTS\\keystore.jks")
            storePassword = "123456"
            keyAlias = "kcn"
            keyPassword = "123456"
        }
    }

    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"

        }
    }

    defaultConfig {
        applicationId = "com.example.uts"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation("androidx.sqlite:sqlite:2.4.0")

    implementation("com.google.api-client:google-api-client:2.7.1")
    implementation("com.google.api-client:google-api-client-android:2.7.1")
    implementation("com.google.api-client:google-api-client-gson:2.7.1")

    // OAuth dan HTTP Client
    implementation("com.google.oauth-client:google-oauth-client:1.36.0")
    implementation("com.google.http-client:google-http-client-android:1.45.3")

    // Google Sign-In dan Calendar
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("com.google.apis:google-api-services-calendar:v3-rev20240328-2.0.0")



    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}