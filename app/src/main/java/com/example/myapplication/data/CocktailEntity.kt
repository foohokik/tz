package com.example.myapplication.data

import androidx.core.net.toUri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.domain.Cocktail

@Entity(tableName = "cocktails")
data class CocktailEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "cocktail_id") val cocktailId: Long = 0,
	@ColumnInfo(name = "image") val image: String?,
	@ColumnInfo(name = "cocktail_name") val cocktailName: String,
	@ColumnInfo(name = "recipe") val recipe: String?,
	@ColumnInfo(name = "description") val description: String?,
	@TypeConverters(IngredientsConverter::class)
	@ColumnInfo(name = "ingredients") val ingredients: List<String>
) {

	fun toDomain(): Cocktail {
		return Cocktail(
			id = cocktailId,
			name = cocktailName,
			recipe = recipe,
			description = description,
			image = image?.toUri(),
			ingredients = ingredients
		)
	}
}