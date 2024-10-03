package com.group.githubfinder.di

import android.app.Application
import com.group.githubfinder.data.repository.FavoriteRepository

object Injection {
    fun provideRepository(application: Application): FavoriteRepository {
        return FavoriteRepository.getInstance(application)
    }
}