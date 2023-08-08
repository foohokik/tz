package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.NetworkResult
import com.example.myapplication.di.DefaultDispatcher
import com.example.myapplication.domain.MainData
import com.example.myapplication.domain.CocktailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

	private val _mainSideEffect = Channel<Unit>()
	val mainSideEffect = _mainSideEffect.receiveAsFlow()

	init {
		viewModelScope.launch {
			_mainSideEffect.send(Unit)
		}
	}
}