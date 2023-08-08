package com.example.myapplication.presentation.show_cocktails_feature.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.domain.ProductItem

class CocktailDiffUtilCallback(
	private val oldList: List<CocktailItem>,
	private val newList: List<CocktailItem>
) : DiffUtil.Callback() {
	override fun getOldListSize(): Int = oldList.size

	override fun getNewListSize(): Int = newList.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val oldItem = oldList[oldItemPosition]
		val newItem = newList[newItemPosition]
		return if (oldItem is ProductItem && newItem is ProductItem) {
			oldItem.id == newItem.id
		} else {
			oldItem.javaClass == newItem.javaClass
		}
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}
}