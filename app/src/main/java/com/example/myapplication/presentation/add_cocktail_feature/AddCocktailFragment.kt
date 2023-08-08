package com.example.myapplication.presentation.add_cocktail_feature

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.core.ADD_DIALOG_FRAGMENT_TAG
import com.example.myapplication.core.COCKTAILS_FRAGMENT_TAG
import com.example.myapplication.core.dpToPx
import com.example.myapplication.databinding.AddCocktailFragmentBinding
import com.example.myapplication.domain.IngredientItem
import com.example.myapplication.presentation.add_cocktail_feature.adapter.IngredientsAdapter
import com.example.myapplication.presentation.show_cocktails_feature.CocktailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCocktailFragment : Fragment() {
	private var _binding: AddCocktailFragmentBinding? = null
	private val binding get() = checkNotNull(_binding)

	private val viewModel: AddCocktailViewModel by activityViewModels()

	private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter(viewModel) }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = AddCocktailFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initRecycler()
		initListeners()
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch { viewModel.ingredientsFlow.collect(::renderIngredients) }
				launch { viewModel.sideEffects.collect(::renderSideEffect) }
			}
		}
	}

	private fun initRecycler() {
		binding.recyclerViewIngredients.apply {
			adapter = ingredientsAdapter
			addItemDecoration(
				HorizontalSpaceItemDecorator(
					SPACE_BETWEEN_ITEMS.dpToPx()
				)
			)
		}
	}

	private fun renderIngredients(items: List<IngredientItem>) {
		ingredientsAdapter.setItems(items)
	}

	private fun renderSideEffect(sideEffect: AddCocktailSideEffect) = with(binding) {
		when (sideEffect) {
			is CancelAddCocktail -> {
				requireActivity().supportFragmentManager.beginTransaction()
					.replace(
						R.id.fragment_container_view,
						CocktailsFragment(),
						COCKTAILS_FRAGMENT_TAG
					)
					.commit()
			}
			is NoIngredientsAddCocktail -> {
				Toast.makeText(
					requireContext(),
					getString(R.string.add_ingredients),
					Toast.LENGTH_SHORT
				).show()
			}
			is TitleAddCocktail -> {
				textInputLayoutTitle.error = getString(R.string.text_input_title_helper_text)
				textInputLayoutTitle.boxStrokeColor =
					ContextCompat.getColor(requireContext(), R.color.red)
				textInputLayoutTitle.placeholderTextColor =
					ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red))
			}

			is AddIngredientAddCocktail -> {
				showDialog()
			}

			is BackToCocktails -> {
				requireActivity().supportFragmentManager.beginTransaction()
					.replace(
						R.id.fragment_container_view,
						CocktailsFragment(),
						COCKTAILS_FRAGMENT_TAG
					).commit()
			}

			else -> {

			}
		}
	}

	private fun showDialog() {
		AddIngredientDialogFragment().show(
			requireActivity().supportFragmentManager,
			ADD_DIALOG_FRAGMENT_TAG
		)
	}

	private fun initListeners() = with(binding) {
		imageViewPhoto.setOnClickListener {

		}

		buttonCancel.setOnClickListener {
			viewModel.onCancel()
		}

		buttonSave.setOnClickListener {
			viewModel.onSaveCocktail(
				name = inputTitle.text.toString().trim(),
				description = inputDescription.text.toString().trim(),
				recipe = inputRecipe.text.toString().trim(),
				image = null
			)
		}
	}

	companion object {
		private const val SPACE_BETWEEN_ITEMS = 8
	}
}