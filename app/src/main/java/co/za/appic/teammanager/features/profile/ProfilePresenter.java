package co.za.appic.teammanager.features.profile;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.enums.UserGender;
import co.za.appic.teammanager.models.UserModel;

public class ProfilePresenter extends BaseAsyncPresenter {

    private ProfileView profileView;
    private UserModel user;
    private boolean isEditMode;

    public ProfilePresenter(ProfileView profileView) {
        super(profileView);
        this.profileView = profileView;
        user = sharedPrefsHelper.getLinkedUser();
        syncUser();
    }

    public void syncUser() {
        String userId = firebaseAuth.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(user.getEmployeeType().getDbTable()).child(userId);
        userRef.keepSynced(true);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    UserModel user = getUserFromDataSnapshot(dataSnapshot);
                    profileView.showUserDetails(user.getEmployeeId(), user.getName(), user.getSurname(), user.getEmployeeType(), user.getGender());
                }
                catch (Exception e){
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

    public void saveChanges(String updatedName, String updatedSurname, UserGender userGender){
        String userId = firebaseAuth.getUid();
        DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference().child(user.getEmployeeType().getDbTable());
        DatabaseReference userRef = tableRef.child(userId);

        DatabaseReference nameRef = userRef.child(Constants.DB_NAME);
        nameRef.setValue(updatedName);
        DatabaseReference surnameRef = userRef.child(Constants.DB_SURNAME);
        surnameRef.setValue(updatedSurname);
        DatabaseReference genderRef = userRef.child(Constants.DB_GENDER);
        genderRef.setValue(userGender.getId());
    }

    public void updateProfilePic() {

    }
}