package co.za.appic.teammanager.features.pushnotifications.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import co.za.appic.teammanager.R;
import co.za.appic.teammanager.helpers.NotificationHelper;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> message = null;

        if(remoteMessage.getData().size() > 0){
            message = remoteMessage.getData();
        }

        if(remoteMessage.getNotification() != null) {
            showPushNotification(remoteMessage);
        }
    }

    private void showPushNotification(RemoteMessage remoteMessage){
        NotificationHelper.showPushNotification(this, R.drawable.ic_notify, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}