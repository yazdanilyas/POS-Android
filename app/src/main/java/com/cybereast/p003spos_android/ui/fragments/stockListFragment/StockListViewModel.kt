package com.cybereast.p003spos_android.ui.fragments.stockListFragment

import com.cybereast.p003spos_android.base.BaseViewModel
import com.cybereast.p003spos_android.models.ProductModel

class StockListViewModel : BaseViewModel() {
    var mProductList: MutableList<Any> = ArrayList()
    var mProductModel: ProductModel? = null
}