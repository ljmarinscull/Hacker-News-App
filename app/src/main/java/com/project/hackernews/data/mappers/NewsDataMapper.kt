package com.project.hackernews.data.mappers

import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.model.NewsEntity

class NewsDataMapper : IMapper<NewObject,NewsEntity> {

    override fun map(input: NewObject): NewsEntity {
        return NewsEntity(
                objectID = input.objectID,
                author = input.author,
                title = input.title,
                createdAt = input.createdAt,
                storyTitle = input.storyTitle,
                storyUrl = input.storyUrl,
            )
    }
}