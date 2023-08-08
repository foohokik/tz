package com.example.myapplication.core

import androidx.annotation.StringRes

sealed class Text {
	data class Resource(@StringRes val resId: Int) : Text()
	data class ResourceParams(
		@StringRes val value: Int,
		val args:List<Any>
	) : Text()

	data class Simple(val text: String) : Text()
}
