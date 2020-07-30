package xyz.akko.simplegallery.logic.model

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import com.previewlibrary.enitity.IThumbViewInfo
import org.jetbrains.annotations.Nullable
import java.io.Serializable

data class img_info(
    val actual_preview_height: Int,
    val actual_preview_width: Int,
    val approver_id: Any,
    val author: String,
    val change: Int,
    val created_at: Int,
    val creator_id: Int,
    val file_ext: String,
    val file_size: Int,
    val file_url: String,
    val frames: List<Any>,
    val frames_pending: List<Any>,
    val frames_pending_string: String,
    val frames_string: String,
    val has_children: Boolean,
    val height: Int,
    val id: Int,
    val is_held: Boolean,
    val is_note_locked: Boolean,
    val is_pending: Boolean,
    val is_rating_locked: Boolean,
    val is_shown_in_index: Boolean,
    val jpeg_file_size: Int,
    val jpeg_height: Int,
    val jpeg_url: String,
    val jpeg_width: Int,
    val last_commented_at: Int,
    val last_noted_at: Int,
    val md5: String,
    val parent_id: Int,
    val preview_height: Int,
    val preview_url: String,
    val preview_width: Int,
    val rating: String,
    val sample_file_size: Int,
    val sample_height: Int,
    val sample_url: String,
    val sample_width: Int,
    val score: Int,
    val source: String,
    val status: String,
    val tags: String,
    val updated_at: Int,
    val width: Int
):Serializable