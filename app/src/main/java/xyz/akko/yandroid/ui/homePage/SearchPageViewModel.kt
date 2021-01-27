package xyz.akko.yandroid.ui.homePage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.akko.yandroid.logic.network.SearchPageService
import xyz.akko.yandroid.logic.network.YanderRenetwork
import xyz.akko.yandroid.util.bean.YanderImgInfo

class SearchPageViewModel: ViewModel()
{
    val imgLiveData = MutableLiveData<ArrayList<YanderImgInfo>>()
    val initList = ArrayList<YanderImgInfo>()
    fun search(tags: String)
    {
        GlobalScope.launch {
            //val temp = YanderRenetwork().searchResultGet(tags)
            val retrofit = Retrofit.Builder().baseUrl("https://yande.re/").addConverterFactory(
                GsonConverterFactory.create()).build()
            Log.d("get",retrofit.create(SearchPageService::class.java).resultGet(tags).request().url().toString())

            val temp = retrofit.create(SearchPageService::class.java).resultGet(tags).execute().body()!!
            Log.d("get",temp.toString())
            imgLiveData.postValue(temp)
        }
    }
}