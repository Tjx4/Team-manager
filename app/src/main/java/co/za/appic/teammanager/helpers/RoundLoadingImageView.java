package co.za.appic.teammanager.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.concurrent.atomic.AtomicInteger;
import co.za.appic.teammanager.R;

public class RoundLoadingImageView extends RelativeLayout {
    private Context context;
    private RoundedImageView imageView;
    private AVLoadingIndicatorView loaderView;
    private String loaderType, imageUrl;
    private float radius;
    private int loaderColor, loaderWidth, loaderHeiht;
    private int defImage;

    public RoundLoadingImageView(Context activity, AttributeSet attrs) {
        super(activity, attrs);
        this.context = activity;

        TypedArray ta = activity.obtainStyledAttributes(attrs, R.styleable.MyLoadingImageView, 0, 0);

        try {
            setPadding(0, 0, 0, 0);
            loaderType = ta.getString(R.styleable.MyLoadingImageView_loaderType);
            loaderColor = ta.getInteger(R.styleable.MyLoadingImageView_loaderColor, 0);
            imageUrl = ta.getString(R.styleable.MyLoadingImageView_imageUrl);
            radius = ta.getFloat(R.styleable.MyLoadingImageView_corner_radius, 0);
            defImage = getImageResFromURI(ta.getString(R.styleable.MyLoadingImageView_defImage));
            loaderWidth = ta.getInteger(R.styleable.MyLoadingImageView_loaderWidth, 0);
            loaderHeiht = loaderWidth;

        }
        catch (Exception e) {
            Log.e("RLI_ERROR", ""+e);
        }

        ta.recycle();

        initViews();
        addLoader();
        addImage();

        this.post( new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    setBackground(context.getResources().getDrawable(R.drawable.loader_image_background));
                }
            }
        });
    }

    private int getImageResFromURI(String uri){
        return R.drawable.ic_profpic_dark;
    }

    private void initViews(){
        imageView = new RoundedImageView(context);
        loaderView = new AVLoadingIndicatorView(context);
    }

    public void showLoader(){
        loaderView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
    }

    public void showImage(){
        loaderView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }
    public void showDefImage(){
        imageView.setImageResource(defImage);
        showImage();
    }

    public void showCurrentImage(String url){
        try {
            GlideHelper.loadImageFromInternet(context, url, imageView, defImage);
            showImage();
        }
        catch (Exception e){
            Log.e("RLI_ERROR", ""+e);
        }
    }

    public void showCurrentImage(int uri){
        imageView.setImageResource(uri);
        showImage();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        int width = getWidth();
        int height = getHeight();
    }

    private void addLoader(){
        RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imgParams.addRule(CENTER_HORIZONTAL);
        imgParams.addRule(CENTER_VERTICAL);
        imgParams.width = (int)UnitConverterHelper.pixelToDp(loaderWidth, context);
        imgParams.height = (int)UnitConverterHelper.pixelToDp(loaderWidth, context);
        loaderView.setLayoutParams(imgParams);
        loaderView.setVisibility(View.VISIBLE);
        loaderView.setIndicator(loaderType);
        loaderView.setId(getUniqId());
        loaderView.setIndicatorColor(loaderColor);
        loaderView.refreshDrawableState();

        addView(loaderView);
    }

    public void addImage(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(CENTER_HORIZONTAL);
        params.addRule(CENTER_VERTICAL);
        params.setMargins(0,0,0,0);
        imageView.setLayoutParams(params);
        imageView.setCornerRadius( UnitConverterHelper.pixelToDp(radius, context) );
        imageView.setVisibility(View.INVISIBLE);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(Color.TRANSPARENT);

        addView(imageView);
    }

    public void setImageFromFirebaseStorage(StorageReference firebaseStorage, String imageUrl) {
        showLoader();
        firebaseStorage.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                showCurrentImage(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                showCurrentImage(defImage);
            }
        });
    }

    public ImageView getImageView(){
        return imageView;
    }

    private final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @SuppressLint("NewApi")
    public int getUniqId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }
}