package com.project.hackernews.data.model

import com.google.gson.annotations.SerializedName
import com.project.hackernews.utils.KMoment
import java.text.SimpleDateFormat
import java.util.*

data class NewObject(
    @SerializedName("author")
    var author: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("objectID")
    var objectID: String?,
    @SerializedName("story_title")
    var storyTitle: String?,
    @SerializedName("story_url")
    var storyUrl: String?,
    @SerializedName("title")
    var title: String?
) {
    constructor() : this("","","","","","")

    fun titleToShow(): String {
        if(!storyTitle.isNullOrEmpty())
            return storyTitle as String
        if(!title.isNullOrEmpty())
            return title as String

            return "Not title"
    }

    fun authorAndTimeShow(): String {
        return "$author - ${createdAt?.let { KMoment.getTimeAgo(it) }}"
    }

    fun toNewEntity(): NewsEntity {
        return NewsEntity(
              objectID = objectID,
                author = author,
                 title = title,
             createdAt = createdAt,
            storyTitle = storyTitle,
              storyUrl = storyUrl,
        )
    }
}

fun String.toDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault()).parse(this)
}