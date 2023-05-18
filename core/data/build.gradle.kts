import pl.piotrgorny.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "pl.piotrgorny.data"
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
}

dependencies {
    implementation(project(Modules.Core.model))
    implementation(project(Modules.Core.database))
    implementation(project(Modules.Core.common))

    implementation(Libraries.AndroidX.corutines)
    implementation(Libraries.AndroidX.Room.ktx)
    implementation(Libraries.AndroidX.Paging.runtime)

    implementation(Libraries.jodaTime)
}