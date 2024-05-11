package com.example.githubuser.data.remote.api

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.remote.response.FollowResponse
import com.example.githubuser.data.remote.response.SearchResponse
import com.example.githubuser.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    suspend fun getGithub(
        @Query("q") q: String
    ): SearchResponse

    @GET("users/{login}")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    suspend fun getDetail(
        @Path("login") id: String
    ): UserResponse

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{login}/followers")
    suspend fun getFollower(
        @Path("login") id: String?
    ): List<FollowResponse>

    @Headers("Authorization: token ${BuildConfig.KEY}")
    @GET("users/{login}/following")
    suspend fun getFollowing(
        @Path("login") username: String?
    ): List<FollowResponse>
}