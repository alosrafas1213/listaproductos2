package com.tallercmovil.gamescm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tallercmovil.gamescm.databinding.ActivityMainBinding
import com.tallercmovil.gamescm.databinding.ListElementBinding
import com.tallercmovil.gamescm.model.Game

class Adaptador(context: Context, games: List<Game>, onItemListener: OnItemListener): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    private val games = games
    private val mOnItemListener = onItemListener

    class ViewHolder(binding: ListElementBinding, onItemListener: OnItemListener): RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private val ivThumbnail = binding.ivThumbnail
        private val tvTitle = binding.tvTitle
        private val context = binding.root.context
        private val onItemListener = onItemListener
        private lateinit var game: Game

        init{
            binding.root.setOnClickListener(this)
        }

        fun bindData(item: Game){
            tvTitle.text = item.title

            Glide.with(context)
                .load(item.thumbnail)
                .into(ivThumbnail)

            game = item

        }

        override fun onClick(v: View?) {
            onItemListener.onItemClick(game)
        }

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

}