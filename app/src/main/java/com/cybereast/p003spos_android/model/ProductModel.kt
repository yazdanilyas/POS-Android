package com.cybereast.p003spos_android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductModel(
    @SerializedName("productId")
    var productId: Int?,
    @SerializedName("productName")
    var productName: String?,
    @SerializedName("productPurchasePrice")
    var productPurchasePrice: Double?,
    @SerializedName("productSalePrice")
    var productSalePrice: Double?,
    @SerializedName("productQuantity")
    var productQuantity: Int?,
    @SerializedName("productDetail")
    var productDetail: String?
) : Serializable {
    override fun toString(): String {
        return "ProductModel(productId=$productId, productName=$productName, productPurchasePrice=$productPurchasePrice, productSalePrice=$productSalePrice, productQuantity=$productQuantity)"
    }
}