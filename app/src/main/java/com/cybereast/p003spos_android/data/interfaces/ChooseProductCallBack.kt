package com.cybereast.p003spos_android.data.interfaces

import com.cybereast.p003spos_android.models.ProductModel

interface ChooseProductCallBack {
    fun onProductSelected(product: ProductModel)

}