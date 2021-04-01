package com.project.hackernews.data.backend

import com.project.hackernews.data.model.ApiResponse
import retrofit2.http.*

@JvmSuppressWildcards
interface BackendService {

    @GET("api/v1/search_by_date?query=mobile")
    suspend fun loadNews(): ApiResponse
}
