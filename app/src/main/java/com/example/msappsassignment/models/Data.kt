package com.example.msappsassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (
    tableName = "articles"
)
data class Data(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val category: String?,
    val country: String?,
    val description: String?,
    val image: String?,
    val language: String?,
    val published_at: String?,
    val source: String?,
    val title: String?,
    val url: String?
) : Serializable