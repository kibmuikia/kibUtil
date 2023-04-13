import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

apply(from = "$rootDir/spotless.gradle")

android {
    namespace = "kib.project.fast"
    compileSdk = 33
    //compileSdkPreview = "UpsideDownCake"

    defaultConfig {
        applicationId = "kib.project.fast"
        minSdk = 24
        targetSdk = 33
        //targetSdkPreview = "UpsideDownCake"
        versionCode = 1000
        versionName = "1.0.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val apiKey: String =
        gradleLocalProperties(rootDir).getProperty("apiKey")

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_KEY", apiKey)
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = " - debug"
            buildConfigField("String", "API_KEY", apiKey)
        }
        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".staging"
            versionNameSuffix = " - staging"
            buildConfigField("String", "API_KEY", apiKey)
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
    implementation(libs.bundles.bundle.compose.lifecycle)

    // modules
    implementation(project(":core"))
    implementation(project(":data"))
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

    // lottie compose
    implementation(libs.lottie.compose)

    // accompanist-permissions
    implementation(libs.accompanist.permissions)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // gson
    implementation(libs.gson)

    // play services auth
    implementation(libs.play.services.auth)
    implementation(libs.play.services.auth.api.phone)

    // test
    androidTestImplementation(libs.junit.test.ext)
    implementation(libs.junit.test.ext.ktx)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}