package com.codekan.notes.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.codekan.notes.database.NotesDatabase

// Database Factory works WITHOUT context param in iOS due to that code separated for Android and iOS.
// Also SQLDelight has own driver factory suitable for iOS NativeSqliteDriver.
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(NotesDatabase.Schema, "notes.db")
    }
}