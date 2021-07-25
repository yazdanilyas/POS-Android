package com.cybereast.p003spos_android.ui.fragments.updateStockFragment

import com.cybereast.p003spos_android.base.BaseViewModel
import com.cybereast.p003spos_android.data.enums.DataMode
import com.cybereast.p003spos_android.models.ProductModel

class UpdateStockViewModel : BaseViewModel() {
    var productModel: ProductModel? = null
    var mode: String = DataMode.ADD.toString()
}