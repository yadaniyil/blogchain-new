package com.yadaniil.blogchain.api.models.cryptocompare

import com.google.gson.annotations.SerializedName

class MinersResponse(
        @SerializedName("Response") val response: String,
        @SerializedName("Message") val message: String,
        @SerializedName("Type") val type: Int,
        @SerializedName("MiningData") val idToMiner: Map<String, Miner>
)