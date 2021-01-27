package xyz.akko.yandroid

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import xyz.akko.yandroid.util.MyActivityManager
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FirstLoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyActivityManager().addActivity(this)
        setContentView(R.layout.activity_first_loading)
    }

    override fun onResume() {
        super.onResume()
        val client = OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS).build()
        val request = Request.Builder().url("https://yande.re/").build();
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                showDialog()
            }

            override fun onResponse(call: Call, response: Response) {
                startAct()
            }
        })
    }
    fun showDialog()
    {
        Looper.prepare()
        AlertDialog.Builder(this).apply {
            setTitle("网络错误")
            setMessage("请检查您的网络连接或代理设置")
            setCancelable(false)
            setPositiveButton("ok"){
                    _, _ ->  MyActivityManager().finishAll()
            }
            show()
        }
        Looper.loop()
    }

    override fun onDestroy() {
        super.onDestroy()
        MyActivityManager().finishActivity(this)
    }

    fun startAct()
    {
        startActivity(Intent(this,MainActivity::class.java))
        MyActivityManager().finishActivity(this)
        finish()
    }
}

