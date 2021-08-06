package com.tallercmovil.gamescm.model

import com.google.gson.annotations.SerializedName

class Game {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("title")
    var title: String? = null
}