package xyz.akko.simplegallery.logic.network


import retrofit2.Call
import retrofit2.http.*
import xyz.akko.simplegallery.logic.model.img_info

interface pageLoadService
{
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")
    @GET("post.json")
    fun pageGet(@Query("page") page: Int): Call<ArrayList<img_info>>
}