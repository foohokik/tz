package com.example.myapplication.presentation.detail_feature.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ProductItemBinding
import com.example.myapplication.databinding.SimpleIngredientItemBinding
import com.example.myapplication.databinding.TitleItemBinding
import com.example.myapplication.domain.ProductItem
import com.example.myapplication.domain.TitleItem

class IngredientViewHolder(private val binding: SimpleIngredientItemBinding) :
	RecyclerView.ViewHolder(binding.root) {

	fun bind(item: String) {
		binding.textViewIngredient.text = item
	}
}