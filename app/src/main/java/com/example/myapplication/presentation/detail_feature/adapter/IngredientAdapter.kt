package com.example.myapplication.presentation.detail_feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.SimpleIngredientItemBinding
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.presentation.show_cocktails_feature.adapter.CocktailDiffUtilCallback

class IngredientAdapter : RecyclerView.Adapter<IngredientViewHolder>() {
	private val items = mutableListOf<String>()

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): IngredientViewHolder {
		val binding = SimpleIngredientItemBinding.inflate(
			LayoutInflater.from(viewGroup.context),
			viewGroup,
			false
		)
		return IngredientViewHolder(binding)
	}

	override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
		holder.bind(items[position])
	}

	override fun getItemCount(): Int = items.size

	fun setItems(newItems: List<String>) {
		val diffResult = DiffUtil.calculateDiff(
			IngredientDiffUtilCallback(
				items,
				newItems
			)
		)
		items.clear()
		items.addAll(newItems)
		diffResult.dispatchUpdatesTo(this)
	}
}