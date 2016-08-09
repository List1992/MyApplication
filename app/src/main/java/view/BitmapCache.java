package view;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by 李松涛 on 2016/6/23 09:31.
 * 描述：volley图片缓存
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mcache;

    public BitmapCache() {

        int maxsize = 10 * 1024 * 1024;//设置图片缓存为10M
        mcache = new LruCache<String, Bitmap>(maxsize) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

    }

    @Override
    public Bitmap getBitmap(String url) {
        return mcache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mcache.put(url, bitmap);
    }
}
