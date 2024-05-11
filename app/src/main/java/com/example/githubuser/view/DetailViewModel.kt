package com.example.githubuser.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.Repository
import com.example.githubuser.data.remote.response.UserResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    fun isFavorite(name: String): Boolean = repository.isFavorite(name)

    fun getDetail(githubProfile: String) = repository.detailUser(githubProfile)

    fun setFavUser(user: UserResponse) = viewModelScope.launch {
        repository.insert(user)
    }

    fun deleteFavUser(user: UserResponse) = viewModelScope.launch {
        repository.delete(user)
    }
}