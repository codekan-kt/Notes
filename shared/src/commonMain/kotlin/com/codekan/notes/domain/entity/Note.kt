package com.codekan.notes.domain.entity

import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@Serializable
@ObjCName("Note")
data class Note(
    val id: Long,
    val title: String,
    val content: String
)