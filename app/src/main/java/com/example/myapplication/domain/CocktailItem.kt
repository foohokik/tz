package com.example.myapplication.domain

import android.net.Uri
import com.example.myapplication.core.Text

sealed interface CocktailItem {
	val type: CocktailItemType
}

data class ProductItem(
	override val type: CocktailItemType = CocktailItemType.PRODUCT,
	val id: Long,
	val name: String,
	val image: Uri?
) : CocktailItem

data class TitleItem(
	override val type: CocktailItemType = CocktailItemType.TITLE,
	val title: Text.Resource
) : CocktailItem

enum class CocktailItemType {
	TITLE, PRODUCT
}

