package com.cybereast.p003spos_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LedgerModel(
    @SerializedName("transactionId")
    var transactionId: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("transactionType")
    var transactionType: String? = null,
    @SerializedName("dr")
    var dr: Double? = null,
    @SerializedName("cr")
    var cr: Double? = null
) : Serializable