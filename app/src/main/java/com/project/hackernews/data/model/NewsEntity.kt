package com.project.hackernews.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = "_id") var id: Int = 0,

        @ColumnInfo(name = "author")
        val author: String?,

        @ColumnInfo(name = "objectID")
        var objectID: String?,

        @ColumnInfo(name = "story_title")
        var storyTitle: String?,

        @ColumnInfo(name = "story_url")
        var storyUrl: String?,

        @ColumnInfo(name = "created_at")
        var createdAt: String?,

        @ColumnInfo(name = "title")
        var title: String?,
){
    constructor() : this(0,"","","","","","")

    fun toNew() : NewObject {
        return NewObject(
                author = author!!,
                objectID = objectID!!,
                storyTitle = storyTitle,
                storyUrl = storyUrl,
                createdAt = createdAt!!,
                title = title
        )
    }
}