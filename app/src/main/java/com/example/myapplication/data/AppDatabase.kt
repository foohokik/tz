package com.example.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
	version = 1,
	entities = [
		CocktailEntity::class
	]
)
@TypeConverters(IngredientsConverter::class)
abstract class AppDatabase : RoomDatabase() {
	abstract val cocktailsDao: CocktailsDao
}