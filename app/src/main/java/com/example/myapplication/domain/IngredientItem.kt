package com.example.myapplication.domain

import com.example.myapplication.core.Text

sealed interface IngredientItem {
	val type: IngredientItemType
}

data class SelfIngredientItem(
	override val type: IngredientItemType = IngredientItemType.INGREDIENT,
	val name: String
) : IngredientItem

data class AddIngredientItem(
	override val type: IngredientItemType = IngredientItemType.ADD_INGREDIENT
) : IngredientItem

enum class IngredientItemType {
	INGREDIENT, ADD_INGREDIENT
}