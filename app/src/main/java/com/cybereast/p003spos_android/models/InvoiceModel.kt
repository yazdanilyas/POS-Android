package com.cybereast.p003spos_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class InvoiceModel(
    @SerializedName("invoiceId")
    var invoiceId: String? = null,
    @SerializedName("date")
    var date: Long? = null,
    @SerializedName("invoiceBill")
    var invoiceBill: Int? = null,
    @SerializedName("userUId")
    var userUId: String? = null,
    @SerializedName("productList")
    var productList: ArrayList<ProductModel>? = null
) : Serializable