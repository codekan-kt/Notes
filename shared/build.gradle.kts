import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.skie)

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    val iosTargets = listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
        iosTargets.forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    // XCFramework yapılandırması
    val xcFramework = XCFramework("shared")
    iosTargets.forEach { iosTarget ->
        iosTarget.binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            xcFramework.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sql.delight.common)
            implementation(libs.sql.delight.coroutines)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

        }

        androidMain.dependencies {
            implementation(libs.sql.delight.android)
            implementation(libs.koin.android)
        }

        iosMain.dependencies {
            implementation(libs.sql.delight.ios)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight{
    databases {
        create("NotesDatabase") {
            packageName.set("com.codekan.notes.database")
        }
    }
    linkSqlite = true
}

skie {
    features {
        group {
            FlowInterop.Enabled(true) // Sadece Flow desteğini etkinleştir
            SuspendInterop.Enabled(false) // Gerekirse suspend desteği
        }
    }
}

