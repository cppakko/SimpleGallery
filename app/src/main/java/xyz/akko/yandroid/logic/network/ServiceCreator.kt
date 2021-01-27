package xyz.akko.yandroid.logic.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator
{
    private val client = OkHttpClient()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://yande.re/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}