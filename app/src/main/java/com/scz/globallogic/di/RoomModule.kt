package com.scz.globallogic.di

import android.content.Context
import androidx.room.Room
import com.scz.globallogic.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokemonDatabase::class.java, "PokemonDatabase").build()

    @Singleton
    @Provides
    fun provideDatabase(database: PokemonDatabase) = database.pokemonDao()
}

