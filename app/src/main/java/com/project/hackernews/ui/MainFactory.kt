package com.project.hackernews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.hackernews.data.backend.NewsDataSource
import com.project.hackernews.data.backend.NewsRepository
import com.project.hackernews.data.database.AppDatabase
import com.project.hackernews.ui.main.MainViewModel

/**
 * ViewModel provider factory to instantiate MainViewModel.
 * Required given MainViewModel has a non-empty constructor
 */
class MainFactory(private val db: AppDatabase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                newsRepository = NewsRepository(
                    dataSource = NewsDataSource(db)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
