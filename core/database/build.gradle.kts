import pl.piotrgorny.buildsrc.Libraries

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "pl.piotrgorny.database"
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
    implementation(Libraries.AndroidX.corutines)
    //Room
    implementation(Libraries.AndroidX.Room.runtime)
    implementation(Libraries.AndroidX.Room.ktx)
    annotationProcessor(Libraries.AndroidX.Room.compiler)
    kapt(Libraries.AndroidX.Room.compiler)
    implementation(Libraries.AndroidX.Room.paging)

    implementation(Libraries.jodaTime)

    testImplementation(Libraries.Test.JUnit.junit)
    androidTestImplementation(Libraries.Test.JUnit.junitExt)
    androidTestImplementation(Libraries.Test.espresso)
    androidTestImplementation(Libraries.Test.Compose.jUnit)
    androidTestImplementation(Libraries.Test.truth)
    androidTestImplementation(Libraries.Test.coreTesting)
}