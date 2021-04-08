package com.project.hackernews.data.backend

import com.project.hackernews.data.database.NewsEntityDao
import com.project.hackernews.data.mappers.NewsDataMapper
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.model.NewsEntity
import com.project.hackernews.data.model.toDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val mapper: NewsDataMapper,
    private val newsDAO: NewsEntityDao,
    private val backend: BackendService
    ) : IRepository {

    override suspend fun loadNews(isOnline: Boolean) : List<NewObject> {
        if (isOnline){
            try {
                var finalResult = listOf<NewObject>()
                val response = backend.loadNews()
                if(response.hits.isNotEmpty()) {
                    finalResult = response.hits.sortedByDescending { it.createdAt?.toDate() }
                    insertNews(finalResult)
                }
                return finalResult
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        } else {
            val entities = loadNewsFromDatabase()
            return entities.map { mapper.mapReverse(it) }
        }

        return listOf()
    }

    override suspend fun loadNewsFromDatabase(): ArrayList<NewsEntity> {
        val result = newsDAO.getAll()
        return ArrayList(result)
    }

    override suspend fun insertNews(data: List<NewObject>) {
        val list = data.map {
            mapper.map(it)
        }
        newsDAO.insertEntitiesFromRemote(list)
    }
}