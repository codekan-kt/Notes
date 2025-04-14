package com.codekan.notes.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.codekan.notes.database.NotesDatabase
// Database Factory works with Android local context due to that code separated for Android and iOS.
// Also SQLDelight has own driver factory suitable for android AndroidSqliteDriver.
actual class DatabaseDriverFactory(private val context : Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(NotesDatabase.Schema, context, "notes.db")
    }
}