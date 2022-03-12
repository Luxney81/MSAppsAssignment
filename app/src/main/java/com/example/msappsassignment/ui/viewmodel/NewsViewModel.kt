package com.example.msappsassignment.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.msappsassignment.NewsApplication
import com.example.msappsassignment.models.Data
import com.example.msappsassignment.models.NewsResponse
import com.example.msappsassignment.repository.NewsRepository
import com.example.msappsassignment.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(app: Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {

    val news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    fun getCategoryNews(api: String, category: String) = viewModelScope.launch {
        safeNewsCall(api, category)
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

    private suspend fun safeNewsCall(api: String, category: String){
        news.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getCategoryNews(api, category)
                news.postValue(handleNewsResponse(response))
            } else {
                news.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable){
            when (t){
                is IOException -> news.postValue(Resource.Error("Network Failure"))
                else -> news.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection() : Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return  when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}