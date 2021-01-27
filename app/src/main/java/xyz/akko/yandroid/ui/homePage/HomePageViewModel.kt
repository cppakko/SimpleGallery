package xyz.akko.yandroid.ui.homePage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.akko.yandroid.util.bean.YanderImgInfo
import xyz.akko.yandroid.logic.network.YanderRenetwork

class HomePageViewModel : ViewModel()
{
    val imgLiveData = MutableLiveData<ArrayList<YanderImgInfo>>()
    val initList = ArrayList<YanderImgInfo>()
    val currentPage = MutableLiveData<Int>(1)

    fun nextPage()
    {
        val temp = currentPage.value!!
        currentPage.postValue(temp + 1)
    }

    fun previousPage()
    {
        val temp = currentPage.value!!
        if (temp == 1)
        {
            reloadPage(1)
        }
        else
        {
            currentPage.postValue(temp - 1)
        }
    }

    fun reloadPage(page: Int)
    {
        GlobalScope.launch {
            val temp = YanderRenetwork().pageGet(currentPage.value!!)
            imgLiveData.postValue(temp)
        }
    }

    fun getCurrentPage(): Int = currentPage.value!!
}
