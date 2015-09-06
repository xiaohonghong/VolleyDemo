package com.example.icbc.newsapp.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

/**
 * 缓存下载下来的图片
 * Created by admin on 2015/9/5.
 */
public class BitMapCache implements ImageLoader.ImageCache {
    // 如果想让整个项目共用一个图片缓存,那么这里可以将mCache设置成静态
    private LruCache<String, Bitmap> mCache;

    public BitMapCache() {
        if (mCache == null) {
            // 分配10M的缓存空间
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }
    }

    @Override
    public Bitmap getBitmap(String s) {
        return mCache.get(s);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
        Log.d(getClass().getSimpleName(), "cacheSize/maxSize:" + mCache.size() + "/" + mCache.maxSize());
    }
}
