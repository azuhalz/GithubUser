package com.example.githubuser.view

import androidx.lifecycle.ViewModel
import com.example.githubuser.data.Repository

class FollowViewModel(private val repository: Repository) : ViewModel() {
    fun getFollower(name: String) = repository.followerUser(name)

    fun getFollowing(name: String) = repository.followingUser(name)
}