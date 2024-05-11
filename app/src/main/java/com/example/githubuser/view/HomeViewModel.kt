package com.example.githubuser.view

import androidx.lifecycle.ViewModel
import com.example.githubuser.data.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun searchUser(githubName: String) = repository.findUser(githubName)
}
