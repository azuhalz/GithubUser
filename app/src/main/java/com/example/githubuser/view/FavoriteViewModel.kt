package com.example.githubuser.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.Repository
import com.example.githubuser.data.remote.response.UserResponse

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getAllChanges(): LiveData<List<UserResponse>> = repository.getAllFavorite()
}