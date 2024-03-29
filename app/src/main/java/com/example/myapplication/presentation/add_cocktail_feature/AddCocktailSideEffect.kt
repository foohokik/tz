package com.example.myapplication.presentation.add_cocktail_feature

sealed interface AddCocktailSideEffect

object CancelAddCocktail : AddCocktailSideEffect
object NoIngredientsAddCocktail : AddCocktailSideEffect
object TitleAddCocktail : AddCocktailSideEffect
object AddIngredientAddCocktail : AddCocktailSideEffect
object DialogErrorAddCocktail : AddCocktailSideEffect
object BackToCocktails : AddCocktailSideEffect
