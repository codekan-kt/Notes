plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)

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
            implementation(libs.sql.delight.common)
            implementation(libs.sql.delight.coroutines)
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            implementation(libs.sql.delight.android)
            implementation(libs.koin.android)
        }

        iosMain.dependencies {
            implementation(libs.sql.delight.ios)
            implementation(libs.koin.core)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight{
    databases {
        create("NotesDatabase") {
            packageName.set("com.codekan.notes.database")
        }
    }
}
