package com.example.myapplication.presentation.show_cocktails_feature.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myapplication.databinding.ProductItemBinding
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.domain.ProductItem

class CocktailsViewHolder(private val binding: ViewBinding) :
	RecyclerView.ViewHolder(binding.root) {

	fun bind(item: CocktailItem, listener: CocktailListener) {
		bindProductItem(
			item as ProductItem,
			binding as ProductItemBinding,
			listener
		)
	}

	private fun bindProductItem(
		item: ProductItem,
		binding: ProductItemBinding,
		listener: CocktailListener
	) =
		with(binding) {
			imageViewCocktail.setImageURI(item.image)
			textViewName.text = item.name
			root.setOnClickListener { listener.onClickItem(item.id) }
		}
}