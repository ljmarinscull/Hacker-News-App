package com.project.hackernews.data.backend

import com.project.hackernews.data.model.NewObject

class NewsRepository(private val dataSource: NewsDataSource) {

    suspend fun loadNews(isOnline: Boolean = true) : List<NewObject> = dataSource.loadNews(isOnline)
}