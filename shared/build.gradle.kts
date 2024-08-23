plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.plugin)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.common.ktor.core)
            implementation(libs.common.ktor.encoding)
            implementation(libs.common.ktor.json)
            implementation(libs.common.ktor.logging)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
        }
        androidMain.dependencies {
            implementation(libs.common.ktor.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.common.ktor.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.test.myapplication"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
