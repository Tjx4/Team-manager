package co.za.appic.teammanager.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Callable;

import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;

public class NotificationHelper {

    private Context context;

    public  NotificationHelper(Context context) {
        this.context = context;
    }

    public void showFragmentDialog(String title, int Layout, DialogFragmentHelper newFragment) {
        AppCompatActivity activity = (AppCompatActivity)context;
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Bundle payload = new Bundle();
        payload.putString(Constants.TITLE, title);
        payload.putInt(Constants.LAYOUT, Layout);

        newFragment.setArguments(payload);
        newFragment.show(ft, "dialog");
    }

    public void showShortToast(String message) {
        Toast toast = getToast(message, Toast.LENGTH_SHORT);
        showTheToastIfNotNull(toast);
    }

    public void showLongToast(String message) {
        Toast toast = getToast(message, Toast.LENGTH_LONG);
        showTheToastIfNotNull(toast);
    }

    private void showTheToastIfNotNull(Toast toast){
        if (toast != null)
            toast.show();
    }

    private Toast getToast(String message, int length) {
        try {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.root));
            TextView text = layout.findViewById(R.id.toast_message);
            text.setText(message);
            Toast toast = new Toast(context);
            toast.setView(layout);
            toast.setDuration(length);

            return toast;
        } catch (Exception e) {
            Log.e(Constants.TOAST_ERROR, "" + e);
            return null;
        }
    }

    protected void showAlertMessage(AlertDialog.Builder ab) {
        AlertDialog a = ab.create();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.show();

        a.getButton(a.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.lightText));
        a.getButton(a.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.lightText));
        a.getButton(a.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.lightText));
    }

    protected AlertDialog.Builder getAlertDialogSuccessMessage(String title, String message, String... buttonText) {

        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        AlertDialog.Builder ab = setupBasicMessage(title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.confirm_icon_light);

        return ab;
    }

    protected void showAlertDialogSuccessMessage(String title, String message, String... buttonText) {
        AlertDialog.Builder ab = getAlertDialogSuccessMessage(title, message, buttonText);
        showAlertMessage(ab);
    }

    public void showAlertDialogMessage(String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        AlertDialog.Builder ab = setupBasicMessage(title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.confirm_icon_light);
        showAlertMessage(ab);
    }

    public void showErrorDialog(String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        message = (message != null && !message.isEmpty()) ? message : context.getResources().getString(R.string.generic_error_message);
        AlertDialog.Builder ab = setupBasicMessage(title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.ic_error_light);
        showAlertMessage(ab);
    }

    protected void showConfirmDialog(String title, String message, boolean showNagativeButton, boolean showNutralButton) {
        String posiTiveButtonText = context.getResources().getString(R.string.confirm);
        AlertDialog.Builder ab = setupBasicMessage(title, message, posiTiveButtonText, showNagativeButton, showNutralButton);
        ab.setIcon(R.drawable.confirm_icon_light);
        showAlertMessage(ab);
    }

    protected AlertDialog.Builder getAlertDialogConfirmMessage(String title, String message, boolean showNagativeButton, boolean showNutralButton) {
        String posiTiveButtonText = context.getResources().getString(R.string.confirm);
        AlertDialog.Builder ab = setupBasicMessage(title, message, posiTiveButtonText, showNagativeButton, showNutralButton);
        ab.setIcon(R.drawable.confirm_icon_light);
        return ab;
    }

    protected AlertDialog.Builder setupBasicMessage(String title, String message, String posiTiveButtonText, boolean showNagativeButton, boolean showNutralButton) {

        AlertDialog.Builder ab = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        ab.setMessage(message)
                .setTitle(title)
                .setPositiveButton(posiTiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        if (showNagativeButton) {
            ab.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return ab;
    }

    private AlertDialog.Builder setupProperBasicMessage(String title, String message) {
        AlertDialog.Builder ab = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        ab.setTitle(title).setMessage(message);
        return ab;
    }

}
