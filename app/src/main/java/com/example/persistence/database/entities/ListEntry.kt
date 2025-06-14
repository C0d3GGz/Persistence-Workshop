package com.example.persistence.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ListEntry(
    val name: String,
    val completed: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0

)