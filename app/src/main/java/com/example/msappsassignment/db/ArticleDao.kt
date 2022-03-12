package com.example.msappsassignment.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.msappsassignment.models.Data

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Data): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Data>>

    @Delete
    suspend fun deleteArticle(article: Data)
}