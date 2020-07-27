package xyz.akko.simplegallery.logic

import android.util.Log
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import xyz.akko.simplegallery.logic.network.SGnetwork
import java.lang.Exception

object repository
{
    fun getPageList(page: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val response = SGnetwork().pageGet(page)
            Result.success(response)
        } catch (e: Exception) {
            Log.d("get",e.toString())
        }
        emit(result)
    }
}