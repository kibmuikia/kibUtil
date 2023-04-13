plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.0-1.0.9"
}

android {
    namespace = "kib.project.data"
    compileSdkVersion(33)

    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(33)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("staging") {
            initWith(getByName("debug"))
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(project(":core"))
    implementation(libs.ksp.api)

    // room
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)

    // coroutines
    implementation(libs.coroutines)

    // timber
    implementation(libs.timber)

    // koin
    implementation(libs.koin)

    // retrofit
    implementation(libs.retrofit)

    // gson
    implementation(libs.gson)

    // test
    implementation(libs.test.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test.ext)
    implementation(libs.junit.test.ext.ktx)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.room.testing)
    implementation(libs.coroutines.test)
}