package xyz.akko.simplegallery.ui.homePage

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import xyz.akko.simplegallery.MainActivity
import xyz.akko.simplegallery.logic.model.img_informationItem
import xyz.akko.simplegallery.logic.network.SGnetwork
import xyz.akko.simplegallery.logic.network.pageLoadService
import xyz.akko.simplegallery.logic.network.serviceCreator
import xyz.akko.simplegallery.logic.repository
import xyz.akko.simplegallery.simpleGalleryAPP
import java.lang.Exception

class homePageViewModel : ViewModel()
{
    val imgLiveData = MutableLiveData<ArrayList<img_informationItem>>()
    val initList = ArrayList<img_informationItem>()
}
