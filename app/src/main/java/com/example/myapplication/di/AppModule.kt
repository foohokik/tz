package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.CocktailsDao
import com.example.myapplication.data.IngredientsConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
		return Room.databaseBuilder(context, AppDatabase::class.java, "database")
			.addTypeConverter(IngredientsConverter())
			.build()
	}

	@Provides
	@Singleton
	fun provideContactDao(appDatabase: AppDatabase): CocktailsDao = appDatabase.cocktailsDao
}