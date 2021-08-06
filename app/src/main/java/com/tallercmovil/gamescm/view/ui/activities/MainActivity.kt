package com.tallercmovil.gamescm.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tallercmovil.gamescm.R
import com.tallercmovil.gamescm.databinding.ActivityMainBinding
import com.tallercmovil.gamescm.model.Game
import com.tallercmovil.gamescm.model.GamesApi
import com.tallercmovil.gamescm.view.adapter.Adaptador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Adaptador.OnItemListener {

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG = "LOGS"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gamesApi: GamesApi = retrofit.create(GamesApi::class.java)

        val call: Call<List<Game>> = gamesApi.getGames("cm/games/games_list.php")

        call.enqueue(object: Callback<List<Game>>{
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                Log.d(LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(LOGTAG, "Datos: ${response.body().toString()}")

                with(binding){
                    pbConexion.visibility = View.INVISIBLE
                }

                val adaptador = Adaptador(this@MainActivity, response.body()!!, this@MainActivity)

                val recyclerView = binding.rvMenu

                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                recyclerView.adapter = adaptador

            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.d(LOGTAG, "Error")
                Toast.makeText(this@MainActivity, "No hay conexión", Toast.LENGTH_SHORT).show()

                with(binding){
                    pbConexion.visibility = View.INVISIBLE
                }
            }

        })

    }

    override fun onItemClick(game: Game) {
        val parametros = Bundle()

        parametros.putString("id", game.id)

        val intent = Intent(this, Detalles::class.java)

        intent.putExtras(parametros)

        startActivity(intent)
    }
}