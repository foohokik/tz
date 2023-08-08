package com.example.myapplication.domain

import android.net.Uri
import com.example.myapplication.core.Text

data class Cocktail(
	val id: Long = 0,
	val name: String,
	val description: String?,
	val ingredients: List<String>,
	val recipe: String?,
	val image: Uri?
)

fun List<Cocktail>.toCocktailItems(): List<CocktailItem> {
	return this.map { item -> item.toCocktailItem() }
}

fun Cocktail.toCocktailItem(): CocktailItem {
	return ProductItem(
		id = id,
		name = name,
		image = image
	)
}
