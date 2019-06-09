package co.za.appic.teammanager.helpers;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;
import co.za.appic.teammanager.features.dashboard.worker.WorkerWorkerDashboardActivity;
import co.za.appic.teammanager.fragments.BaseDialogFragment;

public class NotificationHelper {

    public static void showFragmentDialog(AppCompatActivity activity, String title, int Layout, BaseDialogFragment newFragment) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Bundle payload = new Bundle();
        payload.putString(Constants.TITLE, title);
        payload.putInt(Constants.LAYOUT, Layout);

        newFragment.setArguments(payload);
        newFragment.show(ft, "dialog");
    }

    public static void showShortToast(Context context, String message) {
        Toast toast = getToast(context, message, Toast.LENGTH_SHORT);
        showTheToastIfNotNull(toast);
    }

    public static void showLongToast(Context context, String message) {
        Toast toast = getToast(context, message, Toast.LENGTH_LONG);
        showTheToastIfNotNull(toast);
    }


    public static void showShortTopToast(Context context, String message) {
        Toast toast = getToast(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 300);
        showTheToastIfNotNull(toast);
    }

    public static void showShortBottomToast(Context context, String message) {
        Toast toast = getToast(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
        showTheToastIfNotNull(toast);
    }

    private static void showTheToastIfNotNull(Toast toast){
        if (toast != null)
            toast.show();
    }

    private static Toast getToast(Context context, String message, int length) {
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

    public static void showAlertMessage(Context context, AlertDialog.Builder ab) {
        AlertDialog a = ab.create();
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.show();

        a.getButton(a.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.lightText));
        a.getButton(a.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.lightText));
        a.getButton(a.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.lightText));
    }

    private static AlertDialog.Builder getAlertDialogSuccessMessage(Context context, String title, String message, String... buttonText) {

        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.confirm_icon_light);

        return ab;
    }

    public static void showAlertDialogMessage(Context context, String title, String message, String... buttonText) {
        AlertDialog.Builder ab = getAlertDialogMessage(context, title,  message,  buttonText);
        showAlertMessage(context, ab);
    }

    public static AlertDialog.Builder getAlertDialogMessage(Context context, String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.confirm_icon_light);
        return ab;
    }

    public static AlertDialog.Builder getSelectionDialog(Context context, String title, String message, String posiTiveButtonText) {

        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.confirm_icon_light);
        return ab;
    }

    public static void showErrorDialog(Context context, String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        message = (message != null && !message.isEmpty()) ? message : context.getResources().getString(R.string.generic_error_message);
        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.ic_error_light);
        showAlertMessage(context, ab);
    }

    public static void showSuccessDialog(Context context, String title, String message, String... buttonText) {
        String posiTiveButtonText = context.getResources().getString(R.string.ok);

        if (buttonText != null && buttonText.length > 0)
            posiTiveButtonText = buttonText[0];

        message = (message != null && !message.isEmpty()) ? message : context.getResources().getString(R.string.generic_success_message);
        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, false, false);
        ab.setIcon(R.drawable.ic_success_light);
        showAlertMessage(context, ab);
    }


    public void showConfirmDialog(Context context, String title, String message, boolean showNagativeButton, boolean showNutralButton) {
        String posiTiveButtonText = context.getResources().getString(R.string.confirm);
        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, showNagativeButton, showNutralButton);
        ab.setIcon(R.drawable.confirm_icon_light);
        showAlertMessage(context, ab);
    }

    protected AlertDialog.Builder getAlertDialogConfirmMessage(Context context, String title, String message, boolean showNagativeButton, boolean showNutralButton) {
        String posiTiveButtonText = context.getResources().getString(R.string.confirm);
        AlertDialog.Builder ab = setupBasicMessage(context, title, message, posiTiveButtonText, showNagativeButton, showNutralButton);
        ab.setIcon(R.drawable.confirm_icon_light);
        return ab;
    }

    protected static AlertDialog.Builder setupBasicMessage(Context context, String title, String message, String posiTiveButtonText, boolean showNagativeButton, boolean showNutralButton) {

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

    private AlertDialog.Builder setupProperBasicMessage(Context context, String title, String message) {
        AlertDialog.Builder ab = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        ab.setTitle(title).setMessage(message);
        return ab;
    }

    public static void showPushNotification(Context context, int icon, String title, String message){
        Intent intent = new Intent(context, WorkerWorkerDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //Sound
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }
}