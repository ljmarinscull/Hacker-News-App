package com.project.hackernews.data.preferences

import kotlinx.coroutines.flow.Flow

interface IPreferences {
  val newsDeletedIds : Flow<Set<String>>
  suspend fun saveNewsDeletedId(id: String)
}