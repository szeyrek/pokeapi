package com.scz.globallogic.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scz.globallogic.R

@BindingAdapter("loadUrl")
fun loadUrl(view: AppCompatImageView, url: String?) {
    url?.let {
        Glide.with(view.context).load(url).placeholder(R.color.transparent).into(view)
    } ?: view.setImageResource(R.color.transparent)
}

@BindingAdapter("rvAdapter")
fun setRvAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    adapter?.let { view.adapter = it }
}

@BindingAdapter("showIfTrue")
fun showIfTrue(view: View, shouldShow: Boolean) {
    view.visibility = if (shouldShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}