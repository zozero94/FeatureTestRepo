package com.example.coroutinetest.di

import com.example.data.repository.RepositoryImpl
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository = RepositoryImpl()
}