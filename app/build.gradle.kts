import org.gradle.kotlin.dsl.dependencies
import pl.piotrgorny.buildsrc.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "pl.piotrgorny.reverseosmosisfilterreminder"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        applicationId = ConfigurationData.applicationId
        minSdk = ConfigurationData.minSdk
        targetSdk = ConfigurationData.targetSdk
        versionCode = ConfigurationData.versionCode
        versionName = ConfigurationData.versionName

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
        kotlinCompilerExtensionVersion = Libraries.AndroidX.Compose.compilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Modules.Feature.filterSetup))
    implementation(project(Modules.Feature.reminder))

    implementation(Libraries.AndroidX.Core.ktx)
    implementation(Libraries.AndroidX.LifeCycle.lifecycleRuntimeKtx)

    //Compose
    implementation(Libraries.AndroidX.Compose.activity)
    implementation(Libraries.AndroidX.Compose.navigation)
    implementation(Libraries.AndroidX.Compose.Ui.ui)
    implementation(Libraries.AndroidX.Compose.Ui.uiToolingPreview)
    implementation(Libraries.AndroidX.Compose.material)

    debugImplementation(Libraries.AndroidX.Compose.Ui.uiTooling)
    debugImplementation(Libraries.Test.Compose.testManifest)
}