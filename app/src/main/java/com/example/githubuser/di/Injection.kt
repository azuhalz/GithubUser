package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.Repository
import com.example.githubuser.data.local.FavDB
import com.example.githubuser.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = FavDB.getDatabase(context)
        val dao = database.favDao()
        return Repository.getInstance(apiService, dao)
    }
}