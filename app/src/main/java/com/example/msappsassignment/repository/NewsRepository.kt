package com.example.msappsassignment.repository

import com.example.msappsassignment.api.RetrofitInstance
import com.example.msappsassignment.db.ArticleDatabase
import com.example.msappsassignment.models.Data

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getCategoryNews(api: String, category: String) = RetrofitInstance.api.getCategoryNews(api, category)

    suspend fun upsert(article: Data) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Data) = db.getArticleDao().deleteArticle(article)

}