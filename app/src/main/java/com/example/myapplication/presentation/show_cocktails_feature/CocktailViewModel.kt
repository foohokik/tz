package com.example.myapplication.presentation.show_cocktails_feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.core.Text
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.domain.CocktailRepository
import com.example.myapplication.domain.toCocktailItems
import com.example.myapplication.presentation.show_cocktails_feature.adapter.CocktailListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
	private val repository: CocktailRepository
) : ViewModel() {

	private val _cocktailsFlow: MutableStateFlow<List<CocktailItem>> = MutableStateFlow(emptyList())
	val cocktailsFlow: Flow<List<CocktailItem>> = _cocktailsFlow
	private val _errors = Channel<Unit>()
	val errors: Flow<Unit> = _errors.receiveAsFlow()
	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

	}

	init {
		viewModelScope.launch(exceptionHandler) {
			runCatching {
				repository.getAll()
			}.onSuccess { result ->
				_cocktailsFlow.value = result.toCocktailItems()
			}.onFailure {
				_errors.send(Unit)
			}
		}
	}
}