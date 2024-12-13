plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.uts"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.uts"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
        }
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation("androidx.sqlite:sqlite:2.4.0")

    implementation ("com.google.android.gms:play-services-auth:21.3.0")
    implementation ("com.google.api-client:google-api-client-android:2.7.1")
    implementation ("com.google.api-client:google-api-client-gson:2.7.1")
    implementation ("com.google.apis:google-api-services-calendar:v3-rev20241101-2.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}