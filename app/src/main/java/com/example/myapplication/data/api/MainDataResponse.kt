package com.example.myapplication.data.api

import com.google.gson.annotations.SerializedName

data class MainDataResponse(
	@SerializedName("count") val count: Int
)
