package com.example.myapplication.presentation.add_cocktail_feature

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.core.dpToPx

private const val DEFAULT_SPACE_SIZE = 8

internal class HorizontalSpaceItemDecorator(
	private val spaceSize: Int,
	private val lastItemRightSpaceSize: Int = DEFAULT_SPACE_SIZE.dpToPx(),
	private val firstItemLeftSpaceSize: Int = DEFAULT_SPACE_SIZE.dpToPx(),
) : RecyclerView.ItemDecoration() {

	override fun getItemOffsets(
		outRect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State,
	) {
		with(outRect) {
			if (parent.getChildAdapterPosition(view) != 0) {
				left = spaceSize
			} else {
				left = firstItemLeftSpaceSize
			}
			if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
				right = lastItemRightSpaceSize
			}
		}
	}
}