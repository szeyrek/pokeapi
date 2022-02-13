package com.scz.globallogic.component

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.scz.globallogic.R
import com.scz.globallogic.databinding.ViewLoaderBinding

class LoaderView(private val context: Context) {

    private val view = ViewLoaderBinding.inflate(LayoutInflater.from(context))

    private val dialog by lazy {
        Dialog(context, R.style.Theme_Globallogic).apply {
            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
            window?.apply {
                setBackgroundDrawableResource(R.color.transparent)
            }

            setContentView(view.root)
            setCancelable(false)
        }
    }

    private val isShowing = dialog.isShowing

    fun showProgress() {
        if (isShowing.not()) {
            dialog.show()
        }
    }

    fun dismissProgress() {
        if (isShowing) {
            dialog.dismiss()
        }
    }
}


data class LoaderViewState(val shouldShowProgress: Boolean)