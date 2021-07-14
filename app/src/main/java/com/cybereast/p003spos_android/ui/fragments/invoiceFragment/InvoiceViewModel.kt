package com.cybereast.p003spos_android.ui.fragments.invoiceFragment

import androidx.lifecycle.ViewModel
import com.cybereast.p003spos_android.models.ProductModel

class InvoiceViewModel : ViewModel() {
    var mProductList: MutableList<Any> = ArrayList()
    var mProductModel: ProductModel? = null
}