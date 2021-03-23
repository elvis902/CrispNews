package com.example.newzyapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.littlemango.stacklayoutmanager.StackLayout
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter : RecyclerAdapter
    var articles = mutableListOf<Articles>()
    val TAG = "MainActivity"
    var pageNum = 1
     var totalResults:Int = 0
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }




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
//                Log.d(TAG, "Current News idx is ${manager.getFirstVisibleItemPosition()}")
//                Log.d(TAG, "Total Item Count is ${manager.itemCount}")
                if(totalResults > manager.itemCount && manager.getFirstVisibleItemPosition() > manager.itemCount-5)
                {
                    pageNum++;
                    getNews() //Repeated calls are made till we fetch all total news.
                }
                if(position%5 == 0)
                {
                    //For showing Add
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    }
                }
            }

        })

        rv_main.layoutManager = manager

        getNews()
    }

    private fun getNews() {
        val news = NewsServices.newsInstance.getHeadlines("in", pageNum)
        news.enqueue(object:Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("CEEZYCODE", "Error in Fetcing news")
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                var news: News? = response.body()
                if(news != null)
                {
                    totalResults = news.totalResults.toInt()
                   articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }
        })
    }
}