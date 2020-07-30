package xyz.akko.simplegallery

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.previewlibrary.GPreviewBuilder
import com.previewlibrary.ZoomMediaLoader
import kotlinx.android.synthetic.main.activity_img_detail_page.*
import kotlinx.coroutines.runBlocking
import xyz.akko.simplegallery.logic.model.TestImageLoader
import xyz.akko.simplegallery.logic.model.ZoomViewInfo
import xyz.akko.simplegallery.logic.model.img_info
import java.io.File
import java.io.FileOutputStream


class ImgDetailPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_detail_page)
        setSupportActionBar(toolBarInDetail)
        ZoomMediaLoader.getInstance().init(TestImageLoader())
        this.title = ""
        val data = intent.getSerializableExtra("imgData") as img_info

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }

        val headers = LazyHeaders.Builder()
            .addHeader("User-Agent",R.string.User_Agent.toString())
            .addHeader("device-type", "android")
            .build()
        val url = GlideUrl(data.sample_url,headers)
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .override(data.sample_width,data.sample_height)
            .into(imgSimpleView)

        val tagArr = data.tags.split(" ")
        tagArr.map {
            it.replace('_',' ')
        }
        val t = tagArr.toTypedArray()

        author_tag.addTags(arrayOf(data.author))
        tags_tag.addTags(t)

        imgSimpleView.setOnClickListener(){
            val stringList = listOf(ZoomViewInfo(data.sample_url))
            GPreviewBuilder.from(this)
                .setData(stringList)
                .setCurrentIndex(0)
                .setSingleFling(true)
                .setDrag(true)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .start()
        }

        downloadButton.setOnClickListener(){
            Toast.makeText(this,"下载中",Toast.LENGTH_SHORT).show()
            Thread {
                val headers = LazyHeaders.Builder()
                    .addHeader("User-Agent",R.string.User_Agent.toString())
                    .addHeader("device-type", "android")
                    .build()
                val url = GlideUrl(data.jpeg_url,headers)
                val picture = "/storage/emulated/0/DCIM/" + data.id + ".jpg"
                val filetemp = File(picture)
                val out = FileOutputStream(filetemp)
                val file_bitmap = Glide.with(SGGlobalContext.context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()

                file_bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
            }.start()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            android.R.id.home -> finish()
        }
        return true
    }
}