package com.example.newzyapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.littlemango.stacklayoutmanager.StackLayout
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter : RecyclerAdapter
    var articles = mutableListOf<Articles>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = RecyclerAdapter(this, articles)
        rv_main.adapter = adapter
        //rv_main.layoutManager = LinearLayoutManager(this)

        val manager = StackLayoutManager(scrollOrientation = StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        manager.setPagerMode(true)
        manager.setPagerFlingVelocity(3000)

        manager.setItemChangedListener(object: StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                var colour = ColorPicker.getColors()
                main_container.setBackgroundColor(Color.parseColor(colour))
            }

        })

        rv_main.layoutManager = manager

        getNews()
    }

    private fun getNews() {
        val news = NewsServices.newsInstance.getHeadlines("in", 1)
        news.enqueue(object:Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("CEEZYCODE", "Error in Fetcing news")
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                var news: News? = response.body()
                if(news != null)
                {
                   articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }
        })
    }
}