package com.group.githubfinder.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.group.githubfinder.data.database.FavoriteUser
import com.group.githubfinder.data.repository.FavoriteRepository

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun insert(favoriteUser: FavoriteUser) {
        repository.insertFavorite(favoriteUser)
    }

    fun getOneFavoriteUser(login: String): LiveData<FavoriteUser> =
        repository.getOneFavoriteUser(login)

    fun delete(favoriteUser: FavoriteUser) {
        repository.deleteFavorite(favoriteUser)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = repository.getAllFavoriteUsers()

}