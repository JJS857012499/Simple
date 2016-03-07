package jjs.com.simple;

import android.app.Application;

import com.github.obsessive.library.base.BaseAppManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import jjs.com.simple.api.ApiConstants;
import jjs.com.simple.utils.ImageLoaderHelper;
import jjs.com.simple.utils.VolleyHelper;

/**
 * Created by JJS on 2016/2/27.
 */
public class SimpleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHelper.getInstance().init(this);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(ApiConstants.Paths.IMAGE_LOADER_CACHE_PATH));
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
