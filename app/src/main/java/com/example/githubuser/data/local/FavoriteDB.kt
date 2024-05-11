package com.example.githubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuser.data.remote.response.UserResponse

@Database(entities = [UserResponse::class], exportSchema = false, version = 1)
abstract class FavDB : RoomDatabase() {

    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavDB {
            if (INSTANCE == null) {
                synchronized(FavDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavDB::class.java, "favorite_database"
                    ).build()
                }
            }
            return INSTANCE as FavDB
        }
    }
}