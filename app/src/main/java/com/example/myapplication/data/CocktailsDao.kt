package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailsDao {

	@Query("SELECT * FROM cocktails")
	suspend fun getAll(): List<CocktailEntity>

	@Query("SELECT * FROM cocktails where cocktail_id = :id")
	suspend fun getCocktailById(id: Long): CocktailEntity

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(cocktailEntity: CocktailEntity)
}