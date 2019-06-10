package co.za.appic.teammanager.features.forgotPassword;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import co.za.appic.teammanager.base.presenters.BaseAsyncPresenter;
import co.za.appic.teammanager.helpers.StringValidationHelper;

public class ForgotPasswordPresenter extends BaseAsyncPresenter {

    private ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenter(ForgotPasswordView forgotPasswordView) {
        super(forgotPasswordView);
        this.forgotPasswordView = forgotPasswordView;
    }

    public void resetPassword(String email) {
        boolean isValidEmail = StringValidationHelper.isValidEmail(email);

        if (!isValidEmail){
            forgotPasswordView.showInvalidEmail();
            return;
        }

        isBusy = true;

        firebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        forgotPasswordView.hideLoader();

                        if (task.isSuccessful()) {
                            forgotPasswordView.showResetSuccessMessage();
                        }
                        else {
                            forgotPasswordView.showResetFailMessage();
                        }

                        isBusy = false;
                    }
                });
    }
}