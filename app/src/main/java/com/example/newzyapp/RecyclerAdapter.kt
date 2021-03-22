package com.example.newzyapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class RecyclerAdapter(val context: Context, private val list:List<Articles>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_layout, parent, false )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.newsTitle.text = list[position].title
        holder.newsDescription.text = list[position].description
        val imageUrl = list[position].urlToImage
        Glide.with(context).load(imageUrl).into(holder.newsImage)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, list[position].title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL", list[position].url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
            return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage :ImageView = itemView.news_image
        val newsTitle : TextView = itemView.news_title
        val newsDescription : TextView = itemView.news_description

    }

}