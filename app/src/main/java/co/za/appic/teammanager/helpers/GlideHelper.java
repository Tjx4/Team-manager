package co.za.appic.teammanager.helpers;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.MediaStoreSignature;
import java.io.File;

public class GlideHelper {

    public static void loadImageFromInternet(Context context, String url, ImageView imageView, int placeHolderPic) {
        url = nullURIHandler(url);
        GlideApp.with(context).load(url).placeholder(placeHolderPic).fitCenter().into(imageView);
    }

    public static void loadImageFromInternetNoDefImage(Context context, String url, ImageView imageView) {
        url = nullURIHandler(url);
        GlideApp.with(context).load(url).fitCenter().into(imageView);
    }

    public static void cacheFromInternet(Context context, String url) {
        url = nullURIHandler(url);
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    public static void loadImageFromResourceId(Context context,int resourceId, ImageView imageView, int placeHolderPic) {
        GlideApp.with(context).load(resourceId).placeholder(placeHolderPic).fitCenter().into(imageView);
    }

    public static void loadImageFromResourceIdNoPlaceholder(Context context, int resourceId, ImageView imageView) {
        GlideApp.with(context).load(resourceId).fitCenter().into(imageView);
    }

    public static void loadImageFromURI(Context context, String uri, ImageView imageView, int placeHolderPic) {
        int imgRes = 0;
        uri = nullURIHandler(uri);
        Uri uriFromFile = Uri.fromFile(new File(uri));

        if(!uri.isEmpty()){

            String[] charArray = uri.split("/");
            int lastIndex = charArray.length;
            lastIndex = (lastIndex < 1)? 0 : lastIndex - 1;
            String imageNameAndType = charArray[lastIndex];

            String imageName = imageNameAndType.split("\\.")[0];
            imgRes = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        }

        GlideApp.with(context).load(imgRes).placeholder(placeHolderPic).fitCenter().into(imageView);
    }

    public static void loadImageFromInternetWithChangingSigniture(Context context, String url, ImageView imageView, int placeHolderPic) {
        url = nullURIHandler(url);
        long time = System.currentTimeMillis();
        Key signituer = new MediaStoreSignature("png",  time, 1);
        GlideApp.with(context).load(url).placeholder(placeHolderPic).signature(signituer).fitCenter().into(imageView);
    }

    private  static String nullURIHandler(String uri){
        return  (uri == null)? "" : uri;
    }
}