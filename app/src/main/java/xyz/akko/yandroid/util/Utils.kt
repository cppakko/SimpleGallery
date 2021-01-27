package xyz.akko.yandroid.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import xyz.akko.yandroid.R

class Utils : Application()
{
    companion object
    {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate()
    {
        super.onCreate()
        context = applicationContext
    }

    fun getUrlWithHeader(url: String): GlideUrl
    {
        val headers = LazyHeaders.Builder()
            .addHeader("User-Agent", R.string.User_Agent.toString())
            .addHeader("device-type", "android")
            .build()
        return GlideUrl(url,headers)
    }
}