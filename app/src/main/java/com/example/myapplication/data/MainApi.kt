package com.example.myapplication.data

import com.example.myapplication.data.api.MainDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {
	@GET("/")
	suspend fun getMainData(): Response<MainDataResponse>
}