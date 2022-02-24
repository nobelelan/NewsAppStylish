package com.parulson.newsappchezzycode

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var newsAdapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1
    //private var mInterstitialAd: InterstitialAd? = null


    val rvNewsList: RecyclerView
    get() = findViewById(R.id.rvNewsList)
    val container: ConstraintLayout
    get() = findViewById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*//admob code
        MobileAds.initialize(this)
        var adRequest = AdRequest.Builder().build()*/

        /*InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("nubu", adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("nubu", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })*/


        /*mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("nubu", "Ad was dismissed.")
                *//*if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@MainActivity)
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }*//*
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("nubu", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("nubu", "Ad showed fullscreen content.")

            }
        }*/



        newsAdapter = NewsAdapter(this@MainActivity, articles)
        rvNewsList.adapter = newsAdapter
        //rvNewsList.layoutManager = LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d("nubu","First Visible Item- ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d("nubu","Total Count- ${layoutManager.itemCount}")
                if (totalResults > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5){
                    //next page
                    pageNum++
                    getNews()
                }
                /*if (position %5 ==0){
                    if (mInterstitialAd != null) {
                        mInterstitialAd?.show(this@MainActivity)
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                }*/
            }
        })
        rvNewsList.layoutManager = layoutManager

        getNews()
    }

    private fun getNews() {
        Log.d("nubu","Request sent for $pageNum")
        val newss = NewsService.newsInstance.getHeadlines("in",pageNum)
        newss.enqueue(object : Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null){
                    totalResults = news.totalResults
                    articles.addAll(news.articles)
                    newsAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("nubu","Error fetching data.",t)
            }
        })
    }
}