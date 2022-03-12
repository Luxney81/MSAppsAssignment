package com.example.msappsassignment.ui.viewmodel.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.msappsassignment.repository.NewsRepository
import com.example.msappsassignment.ui.viewmodel.NewsViewModel

class NewsViewModelProviderFactory(val app: Application, val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}