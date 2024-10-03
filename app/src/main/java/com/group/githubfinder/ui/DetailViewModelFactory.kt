package com.group.githubfinder.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group.githubfinder.data.repository.FavoriteRepository
import com.group.githubfinder.di.Injection
import com.group.githubfinder.ui.favorite.FavoriteViewModel

class DetailViewModelFactory (
    private val repository: FavoriteRepository,
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailViewModelFactory? = null

        fun getInstance(repository: Application): DetailViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailViewModelFactory(Injection.provideRepository(repository))
            }.also { instance = it }
    }
}