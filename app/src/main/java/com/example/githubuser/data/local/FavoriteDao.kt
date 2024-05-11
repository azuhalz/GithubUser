package com.example.githubuser.data.local


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.remote.response.UserResponse

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(favorite: UserResponse)

    @Query("SELECT * FROM UserResponse")
    fun getAllFav(): LiveData<List<UserResponse>>

    @Query("SELECT * FROM UserResponse WHERE UserResponse.login LIKE :name")
    fun searchFav(name: String): LiveData<List<UserResponse>>

    @Query("SELECT * FROM UserResponse WHERE UserResponse.login = :name")
    fun getFav(name: String): UserResponse

    @Query("SELECT EXISTS(SELECT * FROM UserResponse WHERE UserResponse.login = :name)")
    fun isFav(name: String): Boolean

    @Delete
    suspend fun deleteFav(favorite: UserResponse)
}