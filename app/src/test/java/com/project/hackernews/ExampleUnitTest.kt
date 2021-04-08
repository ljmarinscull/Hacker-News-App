package com.project.hackernews

import com.project.hackernews.data.mappers.NewsDataMapper
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.model.NewsEntity
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testNewsDataMapper_Method_Map(){
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

    @Test
    fun testNewsDataMapper_Method_MapReverse(){
        val mapper = NewsDataMapper()
        val newsEntity = NewsEntity()

        val newsObject = mapper.mapReverse(newsEntity)
        assertEquals(newsEntity.author, newsObject.author)
        assertEquals(newsEntity.createdAt, newsObject.createdAt)
        assertEquals(newsEntity.objectID, newsObject.objectID)
        assertEquals(newsEntity.storyTitle, newsObject.storyTitle)
        assertEquals(newsEntity.storyUrl, newsObject.storyUrl)
        assertEquals(newsEntity.title, newsObject.title)
    }
}