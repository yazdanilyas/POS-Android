package com.cybereast.p003spos_android.ui.fragments.addEditProductFragment

import com.cybereast.p003spos_android.base.BaseViewModel
import com.cybereast.p003spos_android.data.enums.DataMode
import com.cybereast.p003spos_android.model.ProductModel

class AddEditProductViewModel : BaseViewModel() {
    var productModel: ProductModel? = null
    var mode: String = DataMode.ADD.toString()
}