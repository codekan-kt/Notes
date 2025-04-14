package com.codekan.notes.domain.entity

import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
// Serialization is used for data transfer between Kotlin and Swift.
@OptIn(ExperimentalObjCName::class)
@Serializable
// ObjCName is used to expose the class to Swift with a specific name.
@ObjCName("Note")
data class Note(
    val id: Long,
    val title: String,
    val content: String
)