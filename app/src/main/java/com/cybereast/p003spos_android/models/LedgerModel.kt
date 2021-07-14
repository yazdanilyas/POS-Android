package com.cybereast.p003spos_android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LedgerModel(
    @SerializedName("transactionId")
    var transactionId: String? = "",
    @SerializedName("date")
    var date: String? = "",
    @SerializedName("transactionType")
    var transactionType: String? = "",
    @SerializedName("dr")
    var dr: Double? = 0.0,
    @SerializedName("cr")
    var cr: Double? = 0.0
) : Serializable