package com.cybereast.p003spos_android.models

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("addProduct")
    fun addProduct(textView: TextView, productModel: ProductModel?) {
        val selected = productModel?.selectedQuantity ?: 0
        val total = productModel?.productQuantity ?: 0
        if (selected < total)
            textView.text = selected.plus(1).toString()
    }

    @JvmStatic
    @BindingAdapter("removeProduct")
    fun removeProduct(textView: TextView, productModel: ProductModel?) {
        val selected = productModel?.selectedQuantity ?: 0
        if (selected > 0)
            textView.text = selected.minus(1).toString()
    }
}