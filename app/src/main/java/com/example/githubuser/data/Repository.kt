package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuser.data.local.FavDao
import com.example.githubuser.data.remote.api.ApiService
import com.example.githubuser.data.remote.response.FollowResponse
import com.example.githubuser.data.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers

class Repository(
    private val apiService: ApiService,
    private val mFavDao: FavDao,
) {
    fun findUser(githubName: String): LiveData<Resource<List<UserResponse>>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getGithub(githubName)
            emit(Resource.Success(response.items))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun detailUser(githubProfile: String): LiveData<Resource<UserResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                if (mFavDao.isFav(githubProfile)) {
                    emit(Resource.Success(mFavDao.getFav(githubProfile)))
                } else {
                    val response = apiService.getDetail(githubProfile)
                    emit(Resource.Success(response))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    fun followerUser(githubFollower: String): LiveData<Resource<List<FollowResponse>>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getFollower(githubFollower)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun followingUser(githubFollowing: String): LiveData<Resource<List<FollowResponse>>> =
        liveData {
            emit(Resource.Loading)
            try {
                val response = apiService.getFollowing(githubFollowing)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    suspend fun insert(favorite: UserResponse) {
        mFavDao.addFav(favorite)
    }

    fun getAllFavorite(): LiveData<List<UserResponse>> = mFavDao.getAllFav()

    fun isFavorite(name: String): Boolean = mFavDao.isFav(name)

    suspend fun delete(favorite: UserResponse) {
        mFavDao.deleteFav(favorite)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavDao,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, favoriteDao)
        }.also { instance = it }
    }
}