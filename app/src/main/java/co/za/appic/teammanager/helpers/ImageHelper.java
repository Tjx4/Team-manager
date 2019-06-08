package co.za.appic.teammanager.helpers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.models.UserModel;

public class ImageHelper {

    public static String getProfilePicPath(UserModel user){
        return getProfilePicRootPath(user) + user.getProfilePic();
    }

    public static String getProfilePicRootPath(UserModel user){
        return "users"+"/"+user.getFbId()+"/profpic/";
    }

    public static void getImageFromPhone(AppCompatActivity activity) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, 1);
    }

    public static void shareImage(AppCompatActivity activity, Uri uriToImage) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getText(R.string.send_to)));
    }

    public static void uploadFromBitmap(final AppCompatActivity activity, StorageReference firebaseStorage, Bitmap bitmap, String imagePath) {
        StorageReference storageRef = firebaseStorage;
        StorageReference fullImageRef = storageRef.child(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = fullImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onUploadSuccess(activity);
            }
        });
    }

    public static void onUploadSuccess(AppCompatActivity activity) {

    }

}