package com.cybereast.p003spos_android.models

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cybereast.p003spos_android.helper.Converter.getDate

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

    @JvmStatic
    @BindingAdapter("calculateSelectedPrice")
    fun calculateSelectedPrice(textView: TextView, productModel: ProductModel?) {
        val items = productModel?.selectedQuantity ?: 0
        val price = productModel?.productSalePrice ?: 0
        if (items > 0) {
            textView.text = "Rs " + (items * price).toString() + "/-"
        } else
            textView.text = ""
    }

    @JvmStatic
    @BindingAdapter("setDate")
    fun setDate(pTextView: TextView, date: Long) {
        pTextView.context?.let {
            pTextView.text = getDate(date)
        }
    }

}