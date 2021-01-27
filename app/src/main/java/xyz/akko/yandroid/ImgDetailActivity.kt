package xyz.akko.yandroid

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.previewlibrary.GPreviewBuilder
import com.previewlibrary.ZoomMediaLoader
import kotlinx.android.synthetic.main.activity_img_detail_page.*
import xyz.akko.yandroid.util.DetailPageImageLoader
import xyz.akko.yandroid.logic.model.ZoomViewInfo
import xyz.akko.yandroid.util.bean.YanderImgInfo
import xyz.akko.yandroid.util.MyActivityManager
import xyz.akko.yandroid.util.Utils
import java.io.File
import java.io.FileOutputStream


class ImgDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyActivityManager().addActivity(this)
        setContentView(R.layout.activity_img_detail_page)
        setSupportActionBar(toolBarInDetail)
        ZoomMediaLoader.getInstance().init(DetailPageImageLoader())
        this.title = ""
        val data = intent.getSerializableExtra("imgData") as YanderImgInfo

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(this)
            .load(Utils().getUrlWithHeader(data.sample_url))
            .transition(withCrossFade(factory))
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
            val WRSPermissionStr = "android.permission.WRITE_EXTERNAL_STORAGE"
            if (this.checkSelfPermission(WRSPermissionStr) == PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.requestPermissions(this, arrayOf(WRSPermissionStr),0)
            }
            Toast.makeText(this,"下载中",Toast.LENGTH_SHORT).show()
            Thread {
                val picture = "/storage/emulated/0/DCIM/" + data.id + ".jpg"
                val filetemp = File(picture)
                val out = FileOutputStream(filetemp)
                val fileBitmap = Glide.with(Utils.context)
                    .asBitmap()
                    .load(Utils().getUrlWithHeader(data.jpeg_url))
                    .submit()
                    .get()

                fileBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                //TODO 下载完成提示
            }.start()
            Toast.makeText(Utils.context,"下载完成",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        MyActivityManager().finishActivity(this)
    }
}