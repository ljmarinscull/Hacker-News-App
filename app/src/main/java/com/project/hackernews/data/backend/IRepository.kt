package com.project.hackernews.data.backend

import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.model.NewsEntity

interface IRepository {
    suspend fun loadNews(isOnline: Boolean = true) : List<NewObject>
    suspend fun loadNewsFromDatabase(): ArrayList<NewsEntity>
    suspend fun insertNews(data: List<NewObject>)
}
