package co.za.appic.teammanager.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.constants.Constants;

public class NotificationHelper {

    private Context context;

    public  NotificationHelper(Context context) {
        this.context = context;
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
}
