package xyz.akko.yandroid.logic.model;

import android.graphics.Rect;
import android.os.Parcel;
import androidx.annotation.Nullable;
import com.previewlibrary.enitity.IThumbViewInfo;

public class ZoomViewInfo implements IThumbViewInfo {
    private final String url;
    private Rect mBounds;
    private String videoUrl;

    public ZoomViewInfo(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Rect getBounds() {
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
    }

    protected ZoomViewInfo(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ZoomViewInfo> CREATOR = new Creator<ZoomViewInfo>() {
        @Override
        public ZoomViewInfo createFromParcel(Parcel source) {
            return new ZoomViewInfo(source);
        }

        @Override
        public ZoomViewInfo[] newArray(int size) {
            return new ZoomViewInfo[size];
        }
    };
}