package com.tallercmovil.gamescm.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.tallercmovil.gamescm.databinding.ActivityDetallesBinding
import com.tallercmovil.gamescm.model.Game
import com.tallercmovil.gamescm.model.GameDetail
import com.tallercmovil.gamescm.model.GamesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Detalles : AppCompatActivity() {

    private lateinit var binding: ActivityDetallesBinding

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG = "LOGS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getString("id", "0")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gamesApi: GamesApi = retrofit.create(GamesApi::class.java)

        val call: Call<GameDetail> = gamesApi.getGameDetail(id)

        call.enqueue(object: Callback<GameDetail>{
            override fun onResponse(call: Call<GameDetail>, response: Response<GameDetail>) {
                with(binding){
                    pbConexion.visibility = View.INVISIBLE

                    tvTitle.text = "\n" + response.body()?.name

                    tvLongDesc.text = response.body()?.desc

                    Glide.with(this@Detalles)
                        .load(response.body()?.imag_url)
                        .into(ivImage)

                }
            }

            override fun onFailure(call: Call<GameDetail>, t: Throwable) {

            }

        })

    }
}