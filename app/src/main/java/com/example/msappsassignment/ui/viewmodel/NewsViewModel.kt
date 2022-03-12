package com.example.msappsassignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msappsassignment.models.Data
import com.example.msappsassignment.models.NewsResponse
import com.example.msappsassignment.repository.NewsRepository
import com.example.msappsassignment.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    fun getCategoryNews(api: String, category: String) = viewModelScope.launch {
        news.postValue(Resource.Loading())
        val response = newsRepository.getCategoryNews(api, category)
        news.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Data) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Data) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}