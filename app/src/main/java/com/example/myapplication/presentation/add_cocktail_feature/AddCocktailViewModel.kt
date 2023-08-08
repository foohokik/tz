package com.example.myapplication.presentation.add_cocktail_feature

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.core.Text
import com.example.myapplication.domain.AddIngredientItem
import com.example.myapplication.domain.Cocktail
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.domain.CocktailRepository
import com.example.myapplication.domain.IngredientItem
import com.example.myapplication.domain.IngredientItemType
import com.example.myapplication.domain.SelfIngredientItem
import com.example.myapplication.domain.toCocktailItems
import com.example.myapplication.presentation.add_cocktail_feature.adapter.IngredientsListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCocktailViewModel @Inject constructor(
	private val repository: CocktailRepository
) : ViewModel(), IngredientsListener {

	private val _ingredientsFlow: MutableStateFlow<List<IngredientItem>> =
		MutableStateFlow(listOf(AddIngredientItem()))
	val ingredientsFlow: Flow<List<IngredientItem>> = _ingredientsFlow
	private val _sideEffects = Channel<AddCocktailSideEffect>()
	val sideEffects: Flow<AddCocktailSideEffect> = _sideEffects.receiveAsFlow()
	private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

	}

	fun onSaveCocktail(
		image: Uri? = null,
		name: String,
		description: String? = null,
		recipe: String? = null
	) {
		viewModelScope.launch(exceptionHandler) {
			when {
				name.isBlank() -> _sideEffects.send(TitleAddCocktail)
				_ingredientsFlow.value.size < 2 -> _sideEffects.send(NoIngredientsAddCocktail)
				else -> {
					repository.insert(
						Cocktail(
							name = name,
							description = description,
							ingredients = _ingredientsFlow.value.filter { ingredient -> ingredient.type == IngredientItemType.INGREDIENT }
								.map { ingredient -> (ingredient as SelfIngredientItem).name },
							recipe = recipe,
							image = image
						)
					)
					_sideEffects.send(BackToCocktails)
				}
			}
		}
	}

	fun onAddIngredients(ingredient: String) {
		if (ingredient.isBlank()) {
			viewModelScope.launch {
				_sideEffects.send(DialogErrorAddCocktail)
			}
		} else {
			_ingredientsFlow.value =
				listOf(SelfIngredientItem(name = ingredient)) + _ingredientsFlow.value

		}
	}

	fun onCancel() {
		viewModelScope.launch {
			_sideEffects.send(CancelAddCocktail)
		}
	}

	override fun addIngredient() {
		viewModelScope.launch {
			_sideEffects.send(AddIngredientAddCocktail)
		}
	}
}