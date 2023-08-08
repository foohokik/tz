package com.example.myapplication.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class IngredientsConverter {

	@TypeConverter
	fun fromIngredients(ingredients: List<String>): String {
		return ingredients.joinToString(SEPARATOR)
	}

	@TypeConverter
	fun toIngredients(data: String): List<String> {
		return data.split(SEPARATOR).map { it.trim() }
	}

	companion object {
		private const val SEPARATOR = ","
	}
}