package xyz.akko.yandroid.logic.network


import retrofit2.Call
import retrofit2.http.*
import xyz.akko.yandroid.util.bean.YanderImgInfo

interface LoadPageService
{
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")
    @GET("post.json")
    fun pageGet(@Query("page") page: Int): Call<ArrayList<YanderImgInfo>>
}

interface SearchPageService
{
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")
    @GET("post.json")
    fun resultGet(@Query("tags",encoded = true) tags: String): Call<ArrayList<YanderImgInfo>>
}