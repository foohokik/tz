package com.example.myapplication.presentation.add_cocktail_feature.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.IngredientItem
import com.example.myapplication.domain.SelfIngredientItem

class IngredientsDiffUtilCallback(
	private val oldList: List<IngredientItem>,
	private val newList: List<IngredientItem>
) : DiffUtil.Callback() {
	override fun getOldListSize(): Int = oldList.size

	override fun getNewListSize(): Int = newList.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val oldItem = oldList[oldItemPosition]
		val newItem = newList[newItemPosition]
		return if (oldItem is SelfIngredientItem && newItem is SelfIngredientItem) {
			oldItem.name == newItem.name
		} else {
			oldItem.javaClass == newItem.javaClass
		}
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}
}