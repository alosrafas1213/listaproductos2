package com.tallercmovil.gamescm.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
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




class MainActivity : AppCompatActivity(), Adaptador.OnItemListener, SearchView.OnQueryTextListener {

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG = "LOGS"
    private lateinit var binding: ActivityMainBinding
    private lateinit var buscarview: SearchView
    private lateinit var adaptador : Adaptador
    private lateinit var recyclerView : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buscarview = findViewById<SearchView>(R.id.busqueda)
        buscarlistener()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gamesApi: GamesApi = retrofit.create(GamesApi::class.java)

        val call: Call<MutableList<Game>> = gamesApi.getGames("cm/2021-2/products.php")

        call.enqueue(object: Callback<MutableList<Game>>{
            override fun onResponse(call: Call<MutableList<Game>>, response: Response<MutableList<Game>>) {
                Log.d(LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(LOGTAG, "Datos: ${response.body().toString()}")
                Log.d(LOGTAG, "Variable: ${response.body()?.get(1)?.name?.contains("Nintendo")}")
                Log.d(LOGTAG, "Tamano: ${response.body()?.size}")

                with(binding){
                    pbConexion.visibility = View.INVISIBLE
                }

                adaptador = Adaptador(this@MainActivity, response.body()!!, this@MainActivity)

                adaptador.arregloinit()

                recyclerView = binding.rvMenu

                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                recyclerView.adapter = adaptador
    }

            override fun onFailure(call: Call<MutableList<Game>>, t: Throwable) {
                Log.d(LOGTAG, "Error")
                Toast.makeText(this@MainActivity, "No hay conexión", Toast.LENGTH_SHORT).show()

                with(binding){
                    pbConexion.visibility = View.INVISIBLE
                }
            }

        })
    }


    private fun buscarlistener(){
        buscarview.setOnQueryTextListener(this)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.d(LOGTAG, "Escribio ${newText}")
        adaptador.filtro(newText)
        //mostrar(newText)
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.d(LOGTAG, "Entro al listener")
        return false
    }


    override fun onItemClick(game: Game) {
        val parametros = Bundle()

        parametros.putString("id", game.id)

        val intent = Intent(this, Detalles::class.java)

        intent.putExtras(parametros)

        startActivity(intent)
    }
}


