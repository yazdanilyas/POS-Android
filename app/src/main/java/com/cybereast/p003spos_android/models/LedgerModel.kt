package com.cybereast.p003spos_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LedgerModel(
    @SerializedName("transactionId")
    var transactionId: String? = "",
    @SerializedName("date")
    var date: Long? = 0,
    @SerializedName("transactionType")
    var transactionType: String? = "",
    @SerializedName("dr")
    var dr: Int? = 0,
    @SerializedName("cr")
    var cr: Int? = 0,
    @SerializedName("userUId")
    var userUId: String? = null
) : Serializable