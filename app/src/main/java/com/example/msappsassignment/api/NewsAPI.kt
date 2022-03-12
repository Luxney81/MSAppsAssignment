package com.example.msappsassignment.api

import com.example.msappsassignment.models.NewsResponse
import com.example.msappsassignment.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v1/news")
    suspend fun getCategoryNews(
        @Query("access_key")
        apiKey: String = API_KEY,

        @Query("categories")
        category: String = "general",

        @Query("languages")
        language: String = "en"
    ) : Response<NewsResponse>

}