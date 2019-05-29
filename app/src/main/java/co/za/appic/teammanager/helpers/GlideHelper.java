package co.za.appic.teammanager.helpers;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.MediaStoreSignature;
import java.io.File;

public class GlideHelper {
    private AppCompatActivity activity;
    public GlideHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void loadImageFromInternet(String url, ImageView imageView, int placeHolderPic) {
        url = nullURIHandler(url);
        GlideApp.with(activity).load(url).placeholder(placeHolderPic).fitCenter().into(imageView);
    }

    public void loadImageFromInternetNoDefImage(String url, ImageView imageView) {
        url = nullURIHandler(url);
        GlideApp.with(activity).load(url).fitCenter().into(imageView);
    }

    public void cacheFromInternet(String url) {
        url = nullURIHandler(url);
        GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    public void loadImageFromResourceId(int resourceId, ImageView imageView, int placeHolderPic) {
        GlideApp.with(activity).load(resourceId).placeholder(placeHolderPic).fitCenter().into(imageView);
    }

    public void loadImageFromResourceIdNoPlaceholder(int resourceId, ImageView imageView) {
        GlideApp.with(activity).load(resourceId).fitCenter().into(imageView);
    }

    public void loadImageFromURI(String uri, ImageView imageView, int placeHolderPic) {
        int imgRes = 0;
        uri = nullURIHandler(uri);
        Uri uriFromFile = Uri.fromFile(new File(uri));

        if(!uri.isEmpty()){

            String[] charArray = uri.split("/");
            int lastIndex = charArray.length;
            lastIndex = (lastIndex < 1)? 0 : lastIndex - 1;
            String imageNameAndType = charArray[lastIndex];

            String imageName = imageNameAndType.split("\\.")[0];
            imgRes = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        }

        GlideApp.with(activity).load(imgRes).placeholder(placeHolderPic).fitCenter().into(imageView);
    }

    public void loadImageFromInternetWithChangingSigniture(String url, ImageView imageView, int placeHolderPic) {
        url = nullURIHandler(url);
        long time = System.currentTimeMillis();
        Key signituer = new MediaStoreSignature("png",  time, 1);
        GlideApp.with(activity).load(url).placeholder(placeHolderPic).signature(signituer).fitCenter().into(imageView);
    }

    private String nullURIHandler(String uri){
        return  (uri == null)? "" : uri;
    }
}
