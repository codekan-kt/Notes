package com.codekan.notes.data

import app.cash.sqldelight.db.SqlDriver
// Shared code expect class definitions which are implemented in platform specific code.
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}