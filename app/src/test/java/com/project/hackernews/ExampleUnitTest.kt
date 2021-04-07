package com.project.hackernews

import com.project.hackernews.data.mappers.NewsDataMapper
import com.project.hackernews.data.model.NewObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testNewsDataMapper(){
        val mapper = NewsDataMapper()
        val newsObj = NewObject()

        val newsEntity = mapper.map(newsObj)
        assertEquals(newsObj.author, newsEntity.author)
        assertEquals(newsObj.createdAt, newsEntity.createdAt)
        assertEquals(newsObj.objectID, newsEntity.objectID)
        assertEquals(newsObj.storyTitle, newsEntity.storyTitle)
        assertEquals(newsObj.storyUrl, newsEntity.storyUrl)
        assertEquals(newsObj.title, newsEntity.title)
    }
}