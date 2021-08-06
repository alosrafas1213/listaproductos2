package com.tallercmovil.gamescm.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GamesApi {

    @GET
    fun getGames(
        @Url url: String?
    ): Call<List<Game>>


    @GET("cm/games/game_detail.php?")
    fun getGameDetail(
        @Query("id") id: String?//,
        //@Query("name") name: String?
    ): Call<GameDetail>

    //getGameDetail("21357")

}