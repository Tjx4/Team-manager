package co.za.appic.teammanager.features.profile;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.helpers.ConverterHelper;
import co.za.appic.teammanager.helpers.ImageHelper;
import co.za.appic.teammanager.helpers.StringValidationHelper;
import co.za.appic.teammanager.models.UserModel;

public class ProfilePresenter extends BaseAsyncPresenter {

    private ProfileView profileView;
    private UserModel user;
    private boolean isEditMode;

    public ProfilePresenter(ProfileView profileView) {
        super(profileView);
        this.profileView = profileView;
        user = sharedPrefsHelper.getLinkedUser();
    }

    public void syncUser() {
        final String userId = firebaseAuth.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(user.getEmployeeType().getDbTable()).child(userId);
        userRef.keepSynced(true);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    UserModel user = getUserFromDataSnapshot(dataSnapshot);
                    String ppUrl = ImageHelper.getProfilePicPath(user);
                    profileView.showContent();
                    profileView.showUserDetails(user.getEmployeeId(), user.getName(), user.getSurname(), user.getEmployeeType(), user.getGender(), ppUrl);
                }
                catch (Exception e){
                    Log.e("SYNC_USER_ERROR", ""+e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void saveChanges(String updatedName, String updatedSurname, UserGender updatedGender){

        if(!StringValidationHelper.isValidName(updatedName) || !StringValidationHelper.isValidName(updatedSurname)){
            return;
        }

        String userId = firebaseAuth.getUid();
        DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference().child(user.getEmployeeType().getDbTable());
        DatabaseReference userRef = tableRef.child(userId);

        DatabaseReference nameRef = userRef.child(Constants.DB_NAME);
        nameRef.setValue(updatedName);
        DatabaseReference surnameRef = userRef.child(Constants.DB_SURNAME);
        surnameRef.setValue(updatedSurname);
        DatabaseReference genderRef = userRef.child(Constants.DB_GENDER);
        genderRef.setValue(updatedGender.getId());

        user.setName(updatedName);
        user.setSurname(updatedSurname);
        user.setGender(updatedGender);

        switch (user.getEmployeeType()){
            case worker:
                sharedPrefsHelper.setWorker(ConverterHelper.getWorkerFromUser(user));
                break;
            case supervisor:
                sharedPrefsHelper.setSupervisor(ConverterHelper.getSupervisorFromUser(user));
                break;
        }

        sharedPrefsHelper.setLinkedUser(user);
    }

    public void updateProfilePic(Bitmap bitmap) {
        final String newProfPic = "propic.jpeg";
        String imagePath = ImageHelper.getProfilePicRootPath(user)+newProfPic;

        OnSuccessListener successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String userId = firebaseAuth.getUid();
                DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference().child(user.getEmployeeType().getDbTable());
                DatabaseReference userRef = tableRef.child(userId);

                DatabaseReference profPicRef = userRef.child(Constants.DB_PROFILE_PIC);
                profPicRef.setValue(newProfPic);
            }
        };

        OnFailureListener failureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                profileView.showImageUploadErrorToast();
            }
        };

        ImageHelper.uploadFromBitmap((AppCompatActivity) context, getFirebaseStorage(), bitmap, imagePath, successListener, failureListener);
    }
}