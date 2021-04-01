package com.project.hackernews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.hackernews.data.model.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsEntityDao(): NewsEntityDao
}