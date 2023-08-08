package com.example.myapplication.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Singleton
	@Provides
	fun provideRetrofit(
		okHttpClient: OkHttpClient,
		gson: Gson
	): Retrofit {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(okHttpClient)
			.build()
	}

	@Provides
	@Singleton
	fun provideGson(): Gson = GsonBuilder().create()

	@Singleton
	@Provides
	fun provideOkHttpClient(): OkHttpClient {
		return OkHttpClient
			.Builder()
			.build()
	}
}