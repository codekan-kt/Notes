import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)  // Kotlin Multiplatform plugin
    alias(libs.plugins.androidLibrary)  // Android Library plugin
    alias(libs.plugins.sqlDelight)  // SqlDelight plugin
    alias(libs.plugins.kotlinSerialization)  // Kotlin Serialization plugin
    alias(libs.plugins.skie)  // Skie plugin for iOS using flows and suspend functions written in Kotlin

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    // iOS targets combining in here
    val iosTargets = listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
        iosTargets.forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            binaryOption("bundleId", "com.codekan.notes.shared") // Bundle ID for the iOS framework
        }
    }

    // Creator script property for XCFramework
    val xcFramework = XCFramework("shared")
    iosTargets.forEach { iosTarget ->
        iosTarget.binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            xcFramework.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sql.delight.common) // Dependency for shared module SqlDelight
            implementation(libs.sql.delight.coroutines) // Coroutines support for shared module
            implementation(libs.koin.core) // Koin dependency for DI in shared module
            implementation(libs.kotlinx.serialization.json)  // Kotlin serialization dependency for shared module
            implementation(libs.kotlinx.coroutines.core)  // Coroutines dependency for shared module

        }

        androidMain.dependencies {
            implementation(libs.sql.delight.android) // Dependency for Android SqlDelight
            implementation(libs.koin.android)  // Koin dependency for DI in Android (Dependency Injection suitable for both Android and iOS)
        }

        iosMain.dependencies {
            implementation(libs.sql.delight.ios)  // Dependency for iOS SqlDelight
            implementation(libs.koin.core)  // Koin dependency for DI in iOS (Dependency Injection)
            implementation(libs.kotlinx.coroutines.core) // Coroutines dependency for iOS

        }

    }
}

android {
    namespace = "com.codekan.notes"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17  // Sql Delight works perfect with Java 17
        targetCompatibility = JavaVersion.VERSION_17  // Sql Delight works perfect with Java 17
    }
}

sqldelight{
    databases {  // This is where you define your databases
        create("NotesDatabase") {
            packageName.set("com.codekan.notes.database")
        }
    }
    linkSqlite = true // Enable this if you want to use the SQLite C API in xCode also add libsqlite3.tbd to your xCode project
}

skie {
    features {
        group {
            FlowInterop.Enabled(true) // Flow support for iOS via Skie
            SuspendInterop.Enabled(false) // Suspend Function support for iOS via Skie
        }
    }
}

