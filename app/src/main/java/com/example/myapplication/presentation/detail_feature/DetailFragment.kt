package com.example.myapplication.presentation.detail_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.core.assistedViewModel
import com.example.myapplication.core.dpToPx
import com.example.myapplication.databinding.AddCocktailFragmentBinding
import com.example.myapplication.databinding.DetailFragmentBinding
import com.example.myapplication.domain.Cocktail
import com.example.myapplication.presentation.detail_feature.adapter.IngredientAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

	private var _binding: DetailFragmentBinding? = null
	private val binding get() = checkNotNull(_binding)

	@Inject
	lateinit var viewModelFactory: DetailViewModel.Factory

	private val id by lazy { requireArguments().getLong(ARG_ID) }

	private val viewModel: DetailViewModel by assistedViewModel { viewModelFactory.create(id) }

	private val ingredientAdapter: IngredientAdapter by lazy { IngredientAdapter() }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = DetailFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initRecycler()
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch { viewModel.cocktailFlow.collect(::renderCocktail) }
				launch { viewModel.errors.collect { showError() } }
			}
		}
	}

	private fun showError() {
		Toast.makeText(
			requireContext(),
			getString(R.string.error_get_item_from_database),
			Toast.LENGTH_SHORT
		).show()
	}

	private fun initRecycler() {
		binding.recyclerViewIngredients.apply {
			adapter = ingredientAdapter
			addItemDecoration(
				IngredientDecorator(
					requireContext(),
					SPACE.dpToPx()
				)
			)
		}
	}

	private fun renderCocktail(cocktail: Cocktail?) = with(binding) {
		cocktail ?: return@with
		textViewName.text = cocktail.name
		textViewDescription.text = cocktail.description
		ingredientAdapter.setItems(cocktail.ingredients)
	}

	companion object {
		const val ARG_ID = "ARG_ID"
		private const val SPACE = 16

		@JvmStatic
		fun getInstance(id: Long): DetailFragment {
			return DetailFragment().apply {
				arguments =
					bundleOf(
						ARG_ID to id
					)
			}
		}
	}

}