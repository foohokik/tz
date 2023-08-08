package com.example.myapplication.main

import com.example.myapplication.data.MainApi
import com.example.myapplication.data.CocktailRepositoryImpl
import com.example.myapplication.domain.CocktailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ActivityRetainedComponent::class)
interface MainModule {

	companion object {
		@Provides
		@ActivityRetainedScoped
		fun provideMainApi(retrofit: Retrofit): MainApi = retrofit.create()
	}

	@Binds
	fun bindMainRepository(repository: CocktailRepositoryImpl): CocktailRepository
}