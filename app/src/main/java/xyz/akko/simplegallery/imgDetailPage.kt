package xyz.akko.simplegallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import kotlinx.android.synthetic.main.activity_img_detail_page.*
import kotlinx.android.synthetic.main.activity_main.*
import xyz.akko.simplegallery.logic.model.img_info

class imgDetailPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_detail_page)
        setSupportActionBar(toolBarInDetail)
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
            it.replace("_"," ")
        }
        val t = tagArr.toTypedArray()

        author_tag.addTags(arrayOf(data.author))
        tags_tag.addTags(t)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            android.R.id.home -> finish()
        }
        return true
    }
}