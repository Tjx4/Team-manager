package co.za.appic.teammanager.base.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import co.za.appic.teammanager.base.activities.BaseActivity;
import co.za.appic.teammanager.base.views.BaseView;
import co.za.appic.teammanager.helpers.CacheHelper;
import co.za.appic.teammanager.helpers.GlideHelper;

public abstract class BasePresenter {

    protected Context context;
    private BaseView baseView;
    public CacheHelper cacheHelper;
    public GlideHelper glideHelper;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseDatabase firebaseDatabase;
    protected FirebaseUser firebaseUser;
    public StorageReference firebaseStorage;

    public BasePresenter(BaseView baseView) {
        this.baseView = baseView;
        context = (AppCompatActivity)baseView;
        cacheHelper = new CacheHelper((BaseActivity)baseView);
        glideHelper = new GlideHelper((BaseActivity)baseView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
    }

    public void handleBackButtonPressed(){

    }
}