package com.scz.globallogic.presentation.dashboard

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.scz.globallogic.R
import com.scz.globallogic.base.BaseActivity
import com.scz.globallogic.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ACDashboard : BaseActivity<ActivityDashboardBinding, ACDashboardVM>() {

    private lateinit var navController: NavController
    private lateinit var listener: NavController.OnDestinationChangedListener

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.FRPickPokemon,
                R.id.FRPokemonHistory
            )
        )
    }

    override val layoutId: Int = R.layout.activity_dashboard

    override val viewModel: ACDashboardVM by viewModels()

    override fun initViews() {
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        listener =
            NavController.OnDestinationChangedListener { _, _, arguments ->
                binding.bottomNavigation.isVisible = arguments?.let {
                    !it.getBoolean("hideNavigation", false)
                } ?: true
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }
}