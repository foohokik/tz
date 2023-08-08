package com.example.myapplication.presentation.detail_feature.adapter

import androidx.recyclerview.widget.DiffUtil

class IngredientDiffUtilCallback(
	private val oldList: List<String>,
	private val newList: List<String>
) : DiffUtil.Callback() {
	override fun getOldListSize(): Int = oldList.size

	override fun getNewListSize(): Int = newList.size

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition] == newList[newItemPosition]
	}
}