package com.example.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.core.COCKTAILS_FRAGMENT_TAG
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.show_cocktails_feature.CocktailsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)
		savedInstanceState ?: initFragment()
	}

	private fun initFragment() {
		supportFragmentManager.beginTransaction()
			.add(
				binding.fragmentContainerView.id,
				CocktailsFragment(),
				COCKTAILS_FRAGMENT_TAG
			)
			.commit()
	}
}