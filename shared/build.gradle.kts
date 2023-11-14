plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("co.touchlab.skie") version "0.5.5"
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

        }
    }

    sourceSets {
        val commonMain by sourceSets.getting {
            dependencies {
                implementation("co.touchlab.skie:configuration-annotations:0.5.5")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        /*commonMain {
            dependencies {
                implementation("co.touchlab.skie:configuration-annotations:0.5.5")
                // implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.4")
            }
        }*/
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
    }
}

android {
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
    }
    namespace = "com.jetbrains.android"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}