package com.cybereast.p003spos_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductModel(
    @SerializedName("productId")
    var productId: String? = null,
    @SerializedName("productName")
    var productName: String? = null,
    @SerializedName("productPurchasePrice")
    var productPurchasePrice: Int? = 0,
    @SerializedName("productSalePrice")
    var productSalePrice: Int? = 0,
    @SerializedName("productQuantity")
    var productQuantity: Int? = 0,
    @SerializedName("productDetail")
    var productDetail: String? = null,
    @SerializedName("userUId")
    var userUId: String? = null,
    @SerializedName("selectedQuantity")
    var selectedQuantity: Int? = 0
) : Serializable {
    override fun toString(): String {
        return "ProductModel(productId=$productId, productName=$productName, productPurchasePrice=$productPurchasePrice, productSalePrice=$productSalePrice, productQuantity=$productQuantity)"
    }
}