package com.example.myapplication.presentation.add_cocktail_feature.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myapplication.databinding.AddIngredientItemBinding
import com.example.myapplication.databinding.IngredientItemBinding
import com.example.myapplication.domain.IngredientItem
import com.example.myapplication.domain.IngredientItemType
import com.example.myapplication.domain.SelfIngredientItem

class IngredientsViewHolder(
	private val binding: ViewBinding
) :
	RecyclerView.ViewHolder(binding.root) {

	fun bind(item: IngredientItem, listener: IngredientsListener) {
		when (item.type) {
			IngredientItemType.INGREDIENT -> bindIngredientItem(
				item as SelfIngredientItem,
				binding as IngredientItemBinding
			)
			IngredientItemType.ADD_INGREDIENT -> {
				bindAddIngredientItem(binding as AddIngredientItemBinding, listener)
			}
		}
	}

	private fun bindIngredientItem(item: SelfIngredientItem, binding: IngredientItemBinding) {
		binding.textViewIngredientName.text = item.name
	}

	private fun bindAddIngredientItem(binding: AddIngredientItemBinding, listener: IngredientsListener) {
		binding.root.setOnClickListener {
			listener.addIngredient()
		}
	}
}