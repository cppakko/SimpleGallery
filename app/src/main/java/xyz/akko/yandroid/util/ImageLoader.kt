package xyz.akko.yandroid.util


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.previewlibrary.loader.IZoomMediaLoader
import com.previewlibrary.loader.MySimpleTarget
import org.jetbrains.annotations.Nullable

class DetailPageImageLoader : IZoomMediaLoader {
    override fun displayImage(
        context: Fragment,
        path: String,
        imageView: ImageView,
        simpleTarget: MySimpleTarget
    ) {
        Glide.with(context)
            .asBitmap()
            .load(path)
            .apply(
                RequestOptions().dontAnimate().fitCenter()
            )
            .into(object: CustomTarget<Bitmap>()
            {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView.setImageBitmap(resource)
                    simpleTarget.onResourceReady()
                }
            }
            )
    }

    override fun displayGifImage(
        context: Fragment,
        path: String,
        imageView: ImageView?,
        simpleTarget: MySimpleTarget) {
        Glide.with(context)
            .asGif()
            .load(path)
            .apply(
                RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).dontAnimate()
            )
            .listener(object : RequestListener<GifDrawable?> {
                override fun onLoadFailed(
                    @Nullable e: GlideException?,
                    model: Any,
                    target: Target<GifDrawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    simpleTarget.onResourceReady()
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any,
                    target: Target<GifDrawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    simpleTarget.onLoadFailed(null)
                    return false
                }
            })
            .into(imageView!!)
    }

    override fun onStop(context: Fragment) {
        Glide.with(context).onStop()
    }

    override fun clearMemory(c: Context) {
        Glide.get(c).clearMemory()
    }
}