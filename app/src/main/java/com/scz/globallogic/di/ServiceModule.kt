package com.scz.globallogic.di

import com.scz.globallogic.network.PokeService
import com.scz.globallogic.util.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providePokeService(retrofit: Retrofit): PokeService = retrofit.createService()
}