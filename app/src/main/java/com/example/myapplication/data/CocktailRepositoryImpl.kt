package com.example.myapplication.data

import com.example.myapplication.domain.Cocktail
import com.example.myapplication.domain.CocktailRepository
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
	private val cocktailsDao: CocktailsDao
) : CocktailRepository {

	override suspend fun getAll(): List<Cocktail> {
		return cocktailsDao.getAll().map { cocktail -> cocktail.toDomain() }
	}

	override suspend fun getCocktailById(id: Long): Cocktail {
		return cocktailsDao.getCocktailById(id).toDomain()
	}

	override suspend fun insert(cocktail: Cocktail) {
		val cocktailEntity = with(cocktail) {
			CocktailEntity(
				cocktailName = name,
				description = description,
				ingredients = ingredients,
				recipe = recipe,
				image = image?.toString()
			)
		}
		cocktailsDao.insert(cocktailEntity)
	}
}