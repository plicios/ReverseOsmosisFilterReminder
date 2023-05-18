import pl.piotrgorny.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "pl.piotrgorny.filtersetup"
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        minSdk = ConfigurationData.minSdk
        targetSdk = ConfigurationData.targetSdk

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
    implementation(project(Modules.Core.mvi))
    implementation(project(Modules.Core.model))
    implementation(project(Modules.Core.data))
    implementation(project(Modules.Core.ui))
    implementation(project(Modules.Core.common))
    implementation(project(Modules.Core.navigation))

    implementation(Libraries.AndroidX.Core.ktx)
    implementation(Libraries.AndroidX.LifeCycle.viewModelCompose)

    //Compose
    implementation(Libraries.AndroidX.Compose.activity)
    implementation(Libraries.AndroidX.Compose.navigation)
    implementation(Libraries.AndroidX.Compose.Ui.ui)
    debugImplementation(Libraries.AndroidX.Compose.Ui.uiTooling)
    implementation(Libraries.AndroidX.Compose.Ui.uiToolingPreview)
    implementation(Libraries.AndroidX.Compose.material)
    implementation(Libraries.AndroidX.Compose.constraintLayout)

    implementation(Libraries.jodaTime)
}