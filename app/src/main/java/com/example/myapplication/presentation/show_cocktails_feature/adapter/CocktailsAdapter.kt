package com.example.myapplication.presentation.show_cocktails_feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ProductItemBinding
import com.example.myapplication.databinding.TitleItemBinding
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.domain.CocktailItemType

class CocktailsAdapter(private val listener: CocktailListener) :
	RecyclerView.Adapter<CocktailsViewHolder>() {
	private val items = mutableListOf<CocktailItem>()

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CocktailsViewHolder {
		val binding = ProductItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
		return CocktailsViewHolder(binding)
	}

	override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
		holder.bind(items[position], listener)
	}

	override fun getItemCount(): Int = items.size

	fun setItems(newItems: List<CocktailItem>) {
		val diffResult = DiffUtil.calculateDiff(
			CocktailDiffUtilCallback(
				items,
				newItems
			)
		)
		items.clear()
		items.addAll(newItems)
		diffResult.dispatchUpdatesTo(this)
	}
}