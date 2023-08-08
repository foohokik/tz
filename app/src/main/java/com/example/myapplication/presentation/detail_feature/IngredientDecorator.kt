package com.example.myapplication.presentation.detail_feature

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.core.dpToPx

class IngredientDecorator(private val context: Context, private val space: Int) :
	RecyclerView.ItemDecoration() {

	private val paint =
		Paint(Paint.ANTI_ALIAS_FLAG).apply {
			style = Paint.Style.FILL
			strokeWidth = STROKE_WIDTH.dpToPx()
			color = context.getColor(R.color.hint_text_color)
		}

	override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
		parent.children.forEachIndexed { index, view ->
			if (index != parent.children.count() - 1) {
				setDivider(canvas, view, parent)
			}
		}
	}

	private fun setDivider(
		canvas: Canvas,
		view: View,
		recyclerView: RecyclerView
	) {
		val bottom = view.bottom
		val start = recyclerView.paddingStart
		val end = recyclerView.width.toFloat() - recyclerView.paddingRight
		val center = (end - start) / 2

		canvas.drawLine(
			center - WIDTH.dpToPx(),
			bottom.toFloat() + space,
			center + WIDTH.dpToPx(),
			bottom.toFloat() + space,
			paint
		)
	}

	override fun getItemOffsets(
		rect: Rect,
		view: View,
		parent: RecyclerView,
		state: RecyclerView.State
	) {
		with(rect) {
			if (parent.getChildAdapterPosition(view) != 0) {
				top = space
			}
			if (parent.getChildAdapterPosition(view) != state.itemCount - 1) {
				bottom = space
			}
		}
	}

	companion object {
		private const val WIDTH = 9f
		private const val STROKE_WIDTH = 1f
	}
}