package com.scz.globallogic.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.scz.globallogic.R
import com.scz.globallogic.BR
import com.scz.globallogic.component.LoaderView
import com.scz.globallogic.component.LoaderViewState
import com.scz.globallogic.util.observeNonNull

abstract class BaseActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var binding: BINDING

    protected abstract val viewModel: VM

    protected abstract fun initViews()

    private val loaderView: LoaderView by lazy { LoaderView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.viewModel, this@BaseActivity.viewModel)
        }
        initViews()
        startObservers()
    }

    private fun startObservers() {
        observeLoaderViewState()
        observePopupState()
    }

    private fun observeLoaderViewState() {
        viewModel.loaderViewState.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { model -> handleLoaderViewState(model) }
        }
    }

    private fun showLoaderView() {
        loaderView.showProgress()
    }

    private fun dismissLoaderView() {
        loaderView.dismissProgress()
    }

    private fun observePopupState() {
        viewModel.showErrorPopup.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { message ->
                showErrorPopup(message)
            }
        }
    }

    private fun showPopup(
        withTitle: String, message: String,
        positiveButtonText: String? = null,
        positiveButtonAction: (() -> Unit)? = null,
        negativeButtonText: String? = null,
        negativeButtonAction: (() -> Unit)? = null,
        neutralButtonText: String? = null,
        neutralButtonAction: (() -> Unit)? = null,
        cancelable: Boolean = false
    ) {
        val builder =
            MaterialAlertDialogBuilder(
                this,
                com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Light
            )

        builder.setTitle(withTitle)
        builder.setMessage(message)

        positiveButtonText?.let {
            builder.setPositiveButton(it) { dialog, _ ->
                dialog.dismiss()
                positiveButtonAction?.invoke()
            }
        }

        negativeButtonText?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                dialog.dismiss()
                negativeButtonAction?.invoke()
            }
        }

        neutralButtonText?.let {
            builder.setNeutralButton(it) { dialog, _ ->
                dialog.dismiss()
                neutralButtonAction?.invoke()
            }
        }

        builder.setCancelable(cancelable)

        builder.show()
    }

    fun showErrorPopup(message: String) {
        showPopup(
            withTitle = getString(R.string.error),
            message = message,
            positiveButtonText = getString(R.string.ok)
        )
    }

    fun handleLoaderViewState(loaderViewState: LoaderViewState) {
        if (loaderViewState.shouldShowProgress)
            showLoaderView()
        else
            dismissLoaderView()
    }
}