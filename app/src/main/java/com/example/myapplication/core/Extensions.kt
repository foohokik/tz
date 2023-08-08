package com.example.myapplication.core

import android.content.res.Resources
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

private const val ROUNDING = 0.5f

fun Int.dpToPx(): Int =
	((this + ROUNDING) * Resources.getSystem().displayMetrics.density).roundToInt()

fun Float.dpToPx(): Float =
	((this + ROUNDING) * Resources.getSystem().displayMetrics.density)

fun TextView.setNeededText(clause: Text) {
	when (clause) {
		is Text.Resource -> {
			setText(clause.resId)
		}
		is Text.ResourceParams -> {
			text = context.getString(clause.value, *clause.args.toTypedArray())
		}
		is Text.Simple -> {
			text = clause.text
		}
	}
}

inline fun <reified T : ViewModel> Fragment.assistedViewModel(
	crossinline viewModelProducer: (SavedStateHandle) -> T
): Lazy<T> {
	return viewModels {
		object : AbstractSavedStateViewModelFactory(this@assistedViewModel, arguments) {
			override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
				@Suppress("UNCHECKED_CAST")
				return viewModelProducer(handle) as T
			}
		}
	}
}