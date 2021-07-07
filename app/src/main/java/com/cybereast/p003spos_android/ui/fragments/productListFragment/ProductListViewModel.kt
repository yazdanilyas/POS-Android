package com.cybereast.p003spos_android.ui.fragments.productListFragment

import com.cybereast.p003spos_android.base.BaseViewModel
import com.cybereast.p003spos_android.model.ProductModel

class ProductListViewModel : BaseViewModel() {

    var mProductList: MutableList<Any> = ArrayList()
    var mProductModel: ProductModel? = null
}