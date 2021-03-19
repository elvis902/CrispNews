package com.example.newzyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//http://newsapi.org/v2/everything?q=tesla&from=2021-01-23&sortBy=publishedAt&apiKey=aec04628ce02464c80b47ddbb3a59808

//http://newsapi.org/v2/top-headlines?country=in&apiKey=aec04628ce02464c80b47ddbb3a59808

//http://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=API_KEY

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "aec04628ce02464c80b47ddbb3a59808"
interface NewsInterface{

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page:Int):Call<News>
}

object NewsServices{
    val newsInstance:NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface :: class.java)
    }


}