package xyz.akko.yandroid.ui.homePage

import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import xyz.akko.yandroid.logic.network.YanderRenetwork
import xyz.akko.yandroid.util.Utils
import xyz.akko.yandroid.util.bean.YanderImgInfo

class SearchPageViewModel: ViewModel()
{
    val imgLiveData = MutableLiveData<ArrayList<YanderImgInfo>>()
    val initList = ArrayList<YanderImgInfo>()
    fun search(tags: String)
    {
        CoroutineScope(Dispatchers.IO + Job()).launch{
            val temp = YanderRenetwork().searchResultGet(tags)
            imgLiveData.postValue(temp)
        }
    }
}