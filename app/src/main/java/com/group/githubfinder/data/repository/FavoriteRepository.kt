package com.group.githubfinder.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.group.githubfinder.data.database.FavoriteUser
import com.group.githubfinder.data.database.FavoriteUserDao
import com.group.githubfinder.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository constructor(
    application: Application
) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun insertFavorite(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun getOneFavoriteUser(login: String): LiveData<FavoriteUser> {
        return mFavoriteUserDao.getOneFavoriteUser(login)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            application: Application
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(application)
            }.also { instance = it }
    }
}