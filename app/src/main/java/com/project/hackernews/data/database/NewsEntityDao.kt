package com.project.hackernews.data.database

import androidx.room.*
import com.project.hackernews.data.model.NewsEntity

@Dao
interface NewsEntityDao {

    @Query("SELECT * FROM news")
    suspend fun getAll(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertEntitiesFromRemote(news: List<NewsEntity>) {
        deleteAll()
        insertAll(news)
    }
}