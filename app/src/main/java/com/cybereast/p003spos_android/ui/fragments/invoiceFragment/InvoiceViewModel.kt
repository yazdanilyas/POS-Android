package com.cybereast.p003spos_android.ui.fragments.invoiceFragment

import androidx.lifecycle.ViewModel

class InvoiceViewModel : ViewModel() {
    var mProductList: MutableList<Any> = ArrayList()
    var mBillingAmount: Int = 0
}