package com.techclub.vishesh.kumar.vkit.techclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.model.GameItem

class SimpleGameAdapter(
    private val games: List<GameItem>,
    private val onClick: (GameItem) -> Unit
) : RecyclerView.Adapter<SimpleGameAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGameName: TextView = itemView.findViewById(R.id.tvGameName)
        val imgGameIcon: ImageView = itemView.findViewById(R.id.imgGameIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.tvGameName.text = game.title
        holder.imgGameIcon.setImageResource(R.drawable.ic_launcher_foreground)

        holder.itemView.setOnClickListener {
            onClick(game)
        }
    }

    override fun getItemCount(): Int = games.size
}
