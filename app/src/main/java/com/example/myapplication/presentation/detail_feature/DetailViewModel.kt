package com.example.myapplication.presentation.detail_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.Cocktail
import com.example.myapplication.domain.CocktailRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
	private val repository: CocktailRepository,
	@Assisted id: Long
) : ViewModel() {
	private val _cocktailFlow: MutableStateFlow<Cocktail?> = MutableStateFlow(null)
	val cocktailFlow: Flow<Cocktail?> = _cocktailFlow
	private val _errors = Channel<Unit>()
	val errors: Flow<Unit> = _errors.receiveAsFlow()
	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

	}

	init {
		viewModelScope.launch(exceptionHandler) {
			runCatching {
				repository.getCocktailById(id)
			}.onSuccess { result ->
				_cocktailFlow.value = result
			}.onFailure {
				_errors.send(Unit)
			}
		}
	}

	@AssistedFactory
	interface Factory {
		fun create(id: Long): DetailViewModel
	}
}