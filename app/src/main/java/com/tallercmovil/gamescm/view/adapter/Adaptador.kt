package com.tallercmovil.gamescm.view.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import com.bumptech.glide.Glide
import com.tallercmovil.gamescm.databinding.ActivityMainBinding
import com.tallercmovil.gamescm.databinding.ListElementBinding
import com.tallercmovil.gamescm.model.Game
import com.tallercmovil.gamescm.R

class Adaptador(context: Context, games: MutableList<Game>, onItemListener: OnItemListener): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    private val games = games
    private val mOnItemListener = onItemListener
    private  var itemsOriginales : MutableList<Game> = arrayListOf()
    private val LOGTAG = "LOGS"

    class ViewHolder(binding: ListElementBinding, onItemListener: OnItemListener): RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private val ivThumbnail = binding.ivThumbnail
        private val tvTitle = binding.tvTitle
        private val shortdetails1 = binding.shortdetails1
        private val shortdetails2 = binding.shortdetails2
        private val context = binding.root.context
        val precio: String = context.getString(R.string.precio)
        val envio: String = context.getString(R.string.envio)
        val proveedor: String = context.getString(R.string.proveedor)
        private val onItemListener = onItemListener
        private lateinit var game: Game


        init{
            binding.root.setOnClickListener(this)
        }


        fun bindData(item: Game){

            tvTitle.text = item.name
            val cadenaprecio = "$" + item.price
            var envioprecio = item.delivery
            if (envioprecio=="0.00")
                envioprecio = context.getString(R.string.gratis)
            else
                envioprecio = "$" + envioprecio


            shortdetails1.text = precio + cadenaprecio + "\n" + proveedor + item.provider
            shortdetails2.text = envio + envioprecio
            Glide.with(context)
                .load(item.thumbnail_url)
                .into(ivThumbnail)

            game = item

        }

        override fun onClick(v: View?) {
            onItemListener.onItemClick(game)
        }

    }

    fun arregloinit(){
        itemsOriginales.addAll(games)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adaptador.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding, mOnItemListener)
    }

    override fun onBindViewHolder(holder: Adaptador.ViewHolder, position: Int) {
        holder.bindData(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }

    interface OnItemListener{
        fun onItemClick(game: Game)
    }
    fun filtro(cadena : String){

        Log.d(LOGTAG, "Lenght ${cadena.length}")
        Log.d(LOGTAG, "${cadena} no es ${itemsOriginales?.get(0)?.name}")
        if (cadena.length==0) {
            games.clear()
            games.addAll(itemsOriginales)
        }
        else{
            games.clear()
            for (i in 0..itemsOriginales.size - 1) {
                if (itemsOriginales?.get(i)?.name?.uppercase()?.contains(cadena.uppercase()) == true) {
                    games.add(itemsOriginales?.get(i)!!)
                }
            }
        }
        notifyDataSetChanged()

    }

}