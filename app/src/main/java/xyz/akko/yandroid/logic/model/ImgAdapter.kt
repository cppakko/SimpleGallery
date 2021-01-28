package xyz.akko.yandroid.logic.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.synthetic.main.img_item.view.*
import xyz.akko.yandroid.ImgDetailActivity
import xyz.akko.yandroid.R
import xyz.akko.yandroid.util.Utils.Companion.context
import xyz.akko.yandroid.util.bean.YanderImgInfo


class ImgAdapter(private val imgList: ArrayList<YanderImgInfo>): RecyclerView.Adapter<ImgAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgViewRE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.img_item, parent, false)

        val viewHolder = ViewHolder(view)
        view.imgViewRE.setOnClickListener {
            val position = viewHolder.adapterPosition
            //Log.d("get", imgList[position].tags)
            Intent(context, ImgDetailActivity::class.java).apply {
                putExtra("imgData",imgList[position])
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(context,this,null)
            }
        }

        return viewHolder
    }

    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO need to fix ImgView wid&hei
        val imgItem = imgList[position]
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(context)
            .load(imgItem.preview_url)
            .placeholder(R.drawable.logo)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .override(imgItem.sample_width,imgItem.sample_height)
            .into(holder.img)
    }
}