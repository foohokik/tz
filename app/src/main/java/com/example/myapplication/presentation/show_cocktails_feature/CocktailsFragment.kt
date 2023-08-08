package com.example.myapplication.presentation.show_cocktails_feature

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.core.ADD_COCKTAILS_FRAGMENT_TAG
import com.example.myapplication.core.DETAIL_FRAGMENT_TAG
import com.example.myapplication.core.dpToPx
import com.example.myapplication.databinding.CocktailsFragmentBinding
import com.example.myapplication.domain.CocktailItem
import com.example.myapplication.presentation.add_cocktail_feature.AddCocktailFragment
import com.example.myapplication.presentation.add_cocktail_feature.adapter.IngredientsAdapter
import com.example.myapplication.presentation.detail_feature.DetailFragment
import com.example.myapplication.presentation.show_cocktails_feature.adapter.CocktailListener
import com.example.myapplication.presentation.show_cocktails_feature.adapter.CocktailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailsFragment : Fragment(), CocktailListener {

	private var _binding: CocktailsFragmentBinding? = null
	private val binding get() = checkNotNull(_binding)

	private val viewModel: CocktailViewModel by viewModels()

	private val cocktailsAdapter: CocktailsAdapter by lazy { CocktailsAdapter(this) }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = CocktailsFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initViews()
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch { viewModel.cocktailsFlow.collect(::renderState) }
				launch { viewModel.errors.collect { showError() } }
			}
		}
	}

	override fun onClickItem(id: Long) {
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(
				R.id.fragment_container_view,
				DetailFragment.getInstance(id),
				DETAIL_FRAGMENT_TAG
			)
			.addToBackStack(null)
			.commit()
	}

	private fun renderState(items: List<CocktailItem>) = with(binding) {
		val visibilityCondition = items.size > 1
		recyclerViewCocktails.isVisible = visibilityCondition
		textViewTitleWithRecycler.isVisible = visibilityCondition
		checkVisibility(
			listOf(
				imageViewPlaceholder,
				imageViewArrow,
				textViewTitle,
				textViewDescription
			),
			visibilityCondition.not()
		)
		cocktailsAdapter.setItems(items)
	}

	private fun showError() {
		Toast.makeText(requireContext(), getString(R.string.error_get_database), Toast.LENGTH_SHORT)
			.show()
	}

	private fun checkVisibility(views: List<View>, visibilityCondition: Boolean) {
		views.forEach { view ->
			view.isVisible = visibilityCondition
		}
	}

	private fun initViews() = with(binding) {
		recyclerViewCocktails.apply {
			layoutManager = GridLayoutManager(context, CARDS_GRID_SPAN_COUNT).apply {
				initialPrefetchItemCount = CLIP_PREFETCH_ITEM_COUNT
			}
			addItemDecoration(
				GridSpacingItemDecoration(
					spanCount = CARDS_GRID_SPAN_COUNT,
					spacing = GRID_MARGIN_DP.dpToPx(),
					includeEdge = true
				)
			)
			adapter = cocktailsAdapter
		}

		buttonAddCocktail.setOnClickListener {
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(
					R.id.fragment_container_view,
					AddCocktailFragment(),
					ADD_COCKTAILS_FRAGMENT_TAG
				)
				.addToBackStack(null)
				.commit()
		}
	}

	companion object {
		private const val CARDS_GRID_SPAN_COUNT = 2
		private const val GRID_MARGIN_DP = 8
		private const val CLIP_PREFETCH_ITEM_COUNT = 6
	}
}