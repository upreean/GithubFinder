package com.group.githubfinder.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from favoriteUser ORDER BY login ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE login = :login")
    fun getOneFavoriteUser(login: String): LiveData<FavoriteUser>
}