package com.example.myapplication.presentation.add_cocktail_feature

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.databinding.AddFragmentDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddIngredientDialogFragment : DialogFragment() {
	private val viewModel: AddCocktailViewModel by activityViewModels()

	private var _binding: AddFragmentDialogBinding? = null
	private val binding get() = checkNotNull(_binding)

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = AddFragmentDialogBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupClickListeners()
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch { viewModel.sideEffects.collect(::renderSideEffect) }
			}
		}
	}

	override fun onStart() {
		super.onStart()
		dialog?.window?.apply {
			setLayout(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT
			)
			setBackgroundDrawable(requireContext().getDrawable(R.drawable.dialog_background))
		}
	}

	private fun renderSideEffect(sideEffect: AddCocktailSideEffect) = with(binding) {
		if (sideEffect is DialogErrorAddCocktail) {
			textInputLayoutTitle.error = getString(R.string.text_input_title_helper_text)
			textInputLayoutTitle.boxStrokeColor =
				ContextCompat.getColor(requireContext(), R.color.red)
			textInputLayoutTitle.placeholderTextColor =
				ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red))
		}
	}

	private fun setupClickListeners() = with(binding) {
		buttonSave.setOnClickListener {
			viewModel.onAddIngredients(inputTitle.text.toString().trim())
			if (inputTitle.text.toString().isBlank().not()) {
				dismiss()
			}
		}
		buttonCancel.setOnClickListener {
			dismiss()
		}
	}
}