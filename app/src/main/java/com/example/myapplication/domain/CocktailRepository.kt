package com.example.myapplication.domain

interface CocktailRepository {
	suspend fun getAll(): List<Cocktail>
	suspend fun getCocktailById(id: Long): Cocktail
	suspend fun insert(cocktail: Cocktail)
}