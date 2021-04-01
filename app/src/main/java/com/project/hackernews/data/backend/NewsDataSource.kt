package com.project.hackernews.data.backend

import android.annotation.SuppressLint
import com.project.hackernews.data.database.AppDatabase
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.model.NewsEntity
import com.project.hackernews.data.model.toDate
import com.project.hackernews.utils.KMoment.Companion.dateToLong
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsDataSource(private val db: AppDatabase) {

    suspend fun loadNews(isOnline: Boolean = true) : List<NewObject> {
        if (isOnline){
            try {
                var finalResult = listOf<NewObject>()
                val response = Backend.instance.loadNews()
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
            return entities.map { it.toNew() }
        }

        return listOf()
    }

    private suspend fun loadNewsFromDatabase(): ArrayList<NewsEntity> {
        val result = db.newsEntityDao().getAll()
        return ArrayList(result)
    }

    private suspend fun insertNews(data: List<NewObject>) {
        val list = data.map {
            it.toNewEntity()
        }
        db.newsEntityDao().insertEntitiesFromRemote(list)
    }

}