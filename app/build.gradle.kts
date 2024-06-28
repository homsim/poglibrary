plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.poglibrary.clientapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.poglibrary.clientapp"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


/* old
dependencies {

    implementation(libsApp.androidx.core.ktx)
    implementation(libsApp.androidx.lifecycle.runtime.ktx)
    implementation(libsApp.androidx.activity.compose)
    implementation(platform(libsApp.androidx.compose.bom))
    implementation(libsApp.androidx.ui)
    implementation(libsApp.androidx.ui.graphics)
    implementation(libsApp.androidx.ui.tooling.preview)
    implementation(libsApp.androidx.material3)
    testImplementation(libsApp.junit)
    androidTestImplementation(libsApp.androidx.junit)
    androidTestImplementation(libsApp.androidx.espresso.core)
    androidTestImplementation(platform(libsApp.androidx.compose.bom))
    androidTestImplementation(libsApp.androidx.ui.test.junit4)
    debugImplementation(libsApp.androidx.ui.tooling)
    debugImplementation(libsApp.androidx.ui.test.manifest)
}
*/

dependencies {

    implementation(libsApp.androidx.core.ktx)
    implementation(libsApp.androidx.appcompat)
    implementation(libsApp.material)
    implementation(libsApp.androidx.constraintlayout)
    implementation(libsApp.androidx.navigation.fragment.ktx)
    implementation(libsApp.androidx.navigation.ui.ktx)
    testImplementation(libsApp.junit)
    androidTestImplementation(libsApp.androidx.junit)
    androidTestImplementation(libsApp.androidx.espresso.core)
    implementation(libsApp.ktor.client.core)
    implementation(libsApp.ktor.client.cio)
    implementation(libsApp.ktor.client.okhttp)
    implementation(libsApp.kotlinx.coroutines.core)
    implementation(libsApp.kotlinx.coroutines.android)
    implementation(libsApp.kotlinx.serialization.json)

    implementation(kotlin("reflect"))

    androidTestImplementation(libsApp.androidx.compose.bom) // not sure if this is needed
    // for unit tests
    testImplementation(libsApp.kotlinx.coroutines.test)
    // UI Tests
    androidTestImplementation(libsApp.androidx.ui.test.manifest)
    debugImplementation(libsApp.androidx.ui.test.junit4)

    implementation(libsApp.androidx.compose.bom)
    implementation(libsApp.androidx.compose.material3)

    implementation(libsApp.androidx.ui.tooling.preview)
    debugImplementation(libsApp.androidx.ui.tooling)

    implementation(libsApp.androidx.activity.compose)
    implementation(libsApp.androidx.lifecycle.runtime.ktx)

}