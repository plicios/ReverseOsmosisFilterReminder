import pl.piotrgorny.buildsrc.Libraries

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "pl.piotrgorny.ui"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        minSdk = ConfigurationData.minSdk
        targetSdk = ConfigurationData.targetSdk

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(Libraries.AndroidX.Compose.Ui.ui)
    implementation(Libraries.AndroidX.Compose.material)

    implementation(Libraries.jodaTime)

    implementation(Libraries.AndroidX.Paging.runtime)
    implementation(Libraries.AndroidX.Paging.compose)
}