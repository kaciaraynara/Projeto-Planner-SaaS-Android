package com.plannersaas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val imageUri: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: Long? = null
)
