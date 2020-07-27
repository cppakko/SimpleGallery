package xyz.akko.simplegallery.logic.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object serviceCreator
{
    val client = OkHttpClient()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://yande.re/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}