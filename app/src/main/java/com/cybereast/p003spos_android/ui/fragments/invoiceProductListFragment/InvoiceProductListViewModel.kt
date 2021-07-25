package com.cybereast.p003spos_android.ui.fragments.invoiceProductListFragment

import com.cybereast.p003spos_android.base.BaseViewModel
import com.cybereast.p003spos_android.models.InvoiceModel

class InvoiceProductListViewModel : BaseViewModel() {
    var mProductList: MutableList<Any> = ArrayList()
    var mInvoiceModel: InvoiceModel? = null
}