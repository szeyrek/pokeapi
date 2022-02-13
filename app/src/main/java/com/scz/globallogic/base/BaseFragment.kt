package com.scz.globallogic.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.scz.globallogic.BR
import com.scz.globallogic.util.hideKeyboard

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var binding: BINDING

    private val navController: NavController by lazy { findNavController() }

    private val baseActivity by lazy { requireActivity() as BaseActivity<*, *>? }

    protected abstract val viewModel: VM

    protected open fun observeValues() {}

    protected open fun onErrorReceived() {}

    protected open fun setListeners() {}

    protected open fun initViews() {}


    protected fun navigate(
        navDirections: NavDirections
    ) {
        clearCurrentFocus()

        navController.navigate(
            navDirections.actionId,
            navDirections.arguments
        )
    }

    protected fun clearCurrentFocus() {
        baseActivity?.currentFocus?.let {
            it.hideKeyboard()
            it.clearFocus()
        }
    }

    protected fun setTitle(text: String) {
        baseActivity?.supportActionBar?.title = text
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, this@BaseFragment.viewModel)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        startObservers()
        observeValues()
        setListeners()
    }

    private fun startObservers() {
        observeLoaderViewState()
        observePopupState()
    }

    private fun observeLoaderViewState() {
        viewModel.loaderViewState.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { loaderViewModel ->
                baseActivity?.handleLoaderViewState(loaderViewModel)
            }
        }
    }

    private fun observePopupState() {
        viewModel.showErrorPopup.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                onErrorReceived()
                baseActivity?.showErrorPopup(message)
            }
        }
    }

}