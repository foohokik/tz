package com.example.myapplication.presentation.add_cocktail_feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AddIngredientItemBinding
import com.example.myapplication.databinding.IngredientItemBinding
import com.example.myapplication.domain.IngredientItem
import com.example.myapplication.domain.IngredientItemType

class IngredientsAdapter(private val listener: IngredientsListener) : RecyclerView.Adapter<IngredientsViewHolder>() {
	private val items = mutableListOf<IngredientItem>()

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): IngredientsViewHolder {
		val binding = when (viewType) {
			TYPE_ADD_INGREDIENT -> {
				AddIngredientItemBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			}
			TYPE_INGREDIENT -> {
				IngredientItemBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			}
			else -> {
				IngredientItemBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			}
		}
		return IngredientsViewHolder(binding)
	}

	override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
		holder.bind(items[position], listener)
	}

	override fun getItemCount(): Int = items.size

	override fun getItemViewType(position: Int): Int {
		return when (items[position].type) {
			IngredientItemType.INGREDIENT -> TYPE_INGREDIENT
			IngredientItemType.ADD_INGREDIENT -> TYPE_ADD_INGREDIENT
		}
	}

	fun setItems(newItems: List<IngredientItem>) {
		val diffResult = DiffUtil.calculateDiff(
			IngredientsDiffUtilCallback(
				items,
				newItems
			)
		)
		items.clear()
		items.addAll(newItems)
		diffResult.dispatchUpdatesTo(this)
	}

	companion object {
		private const val TYPE_ADD_INGREDIENT = 0
		private const val TYPE_INGREDIENT = 1
	}
}