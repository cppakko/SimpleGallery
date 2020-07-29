package xyz.akko.simplegallery.ui.homePage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import xyz.akko.simplegallery.logic.model.img_info
import xyz.akko.simplegallery.logic.network.SGnetwork

class homePageViewModel : ViewModel()
{
    val imgLiveData = MutableLiveData<ArrayList<img_info>>()
    val initList = ArrayList<img_info>()
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
            val temp = SGnetwork().pageGet(currentPage.value!!)
            imgLiveData.postValue(temp)
        }
    }

    fun getCurrentPage(): Int = currentPage.value!!
}
