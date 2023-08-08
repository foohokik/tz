package com.example.myapplication.data

import com.example.myapplication.data.api.MainDataResponse
import com.example.myapplication.domain.MainData
import javax.inject.Inject

class MainMapper @Inject constructor() {
	fun mapToDomain(mainDataResponse: MainDataResponse): MainData {
		return MainData(
			count = mainDataResponse.count
		)
	}
}