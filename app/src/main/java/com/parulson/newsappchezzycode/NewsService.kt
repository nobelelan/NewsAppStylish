package com.parulson.newsappchezzycode

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=us&apiKey=40bd27dec5664f2d9b115adcb7bb3cc8
//https://newsapi.org/v2/everything?q=bitcoin&apiKey=40bd27dec5664f2d9b115adcb7bb3cc8

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "40bd27dec5664f2d9b115adcb7bb3cc8"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(
        @Query("country")country: String, @Query("page")page: Int
    ):Call<News>

}

object NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}