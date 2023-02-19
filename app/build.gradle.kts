plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "kib.project.fast"
    compileSdkVersion(33)

    defaultConfig {
        applicationId = "kib.project.fast"
        minSdkVersion(24)
        targetSdkVersion(33)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.bundles.bundle.compose.ui)
    implementation(libs.navigation.compose)

    // coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.test) // test

    // viewmodel-compose
    implementation(libs.lifecycle.viewmodel.compose)

    // modules
    implementation(project(":core"))
    implementation(project(":domain"))

    // koin
    implementation(libs.koin)
    implementation(libs.koin.compose)
    // implementation(libs.koin.junit5) // test

    // timber
    implementation(libs.timber)

    // compose
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.window.sizeclass)

    // test
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}