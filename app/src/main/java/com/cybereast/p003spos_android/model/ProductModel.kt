package com.cybereast.p003spos_android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductModel(
    @SerializedName("productId")
    var productId: String? = "",
    @SerializedName("productName")
    var productName: String? = "",
    @SerializedName("productPurchasePrice")
    var productPurchasePrice: Double? = 0.0,
    @SerializedName("productSalePrice")
    var productSalePrice: Double? = 0.0,
    @SerializedName("productQuantity")
    var productQuantity: Int? = 0,
    @SerializedName("productDetail")
    var productDetail: String? = "",
    @SerializedName("userUId")
    var userUId: String? = ""
) : Serializable {
    override fun toString(): String {
        return "ProductModel(productId=$productId, productName=$productName, productPurchasePrice=$productPurchasePrice, productSalePrice=$productSalePrice, productQuantity=$productQuantity)"
    }
}