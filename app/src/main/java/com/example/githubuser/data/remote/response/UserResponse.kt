package com.example.githubuser.data.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserResponse(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    var login: String = "",

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String?,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    var avatar_url: String = "",

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int
) : Parcelable