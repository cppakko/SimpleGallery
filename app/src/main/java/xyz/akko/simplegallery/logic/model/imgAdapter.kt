package xyz.akko.simplegallery.logic.model

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.img_item.view.*
import xyz.akko.simplegallery.R
import xyz.akko.simplegallery.SGGlobalContext
import xyz.akko.simplegallery.SGGlobalContext.Companion.context
import xyz.akko.simplegallery.ImgDetailPage

class imgAdapter(private val imgList: ArrayList<img_info>): RecyclerView.Adapter<imgAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgViewRE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.img_item, parent, false)

        val viewHolder = ViewHolder(view)
        view.imgViewRE.setOnClickListener(){
            val position = viewHolder.adapterPosition
            val l = imgList[position]
            Log.d("get", imgList[position].tags)
            val intent = Intent(SGGlobalContext.context, ImgDetailPage::class.java).apply {
                putExtra("imgData",imgList[position])
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(SGGlobalContext.context,this,null)
            }
        }

        return viewHolder
    }

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgItem = imgList[position]
        Glide.with(context)
            .load(imgItem.preview_url)
            .override(imgItem.sample_width,imgItem.sample_height)
            .placeholder(R.drawable.ic_baseline_refresh_24)
            .into(holder.img)
    }
}