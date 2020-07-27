package xyz.akko.simplegallery.logic.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import xyz.akko.simplegallery.R
import xyz.akko.simplegallery.simpleGalleryAPP.Companion.context
import xyz.akko.simplegallery.ui.homePage.homePageViewModel

class imgAdapter(val imgList: ArrayList<img_informationItem>): RecyclerView.Adapter<imgAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgViewRE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.img_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgItem = imgList[position]
        Glide.with(context)
            .load(imgItem.preview_url)
            .override(imgItem.sample_width,imgItem.sample_height)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .dontAnimate()
            .into(holder.img)
    }
}