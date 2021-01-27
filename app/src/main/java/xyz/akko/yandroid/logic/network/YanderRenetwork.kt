package xyz.akko.yandroid.logic.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class YanderRenetwork {
    private val pageGetService = ServiceCreator.create<LoadPageService>()
    private val searchGetService = ServiceCreator.create<SearchPageService>()

    suspend fun pageGet (page : Int) = pageGetService.pageGet(page).await()
    suspend fun searchResultGet (tags: String) = searchGetService.resultGet(tags).await()

    private suspend fun <T> Call<T>.awit(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T>
            {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null)
                    {
                        continuation.resume(body)
                    }
                    else continuation.resumeWithException(
                        RuntimeException("NULL")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable)
                {
                    continuation.resumeWithException(t)
                    Log.d("get","out")
                }
            })
        }
    }
}