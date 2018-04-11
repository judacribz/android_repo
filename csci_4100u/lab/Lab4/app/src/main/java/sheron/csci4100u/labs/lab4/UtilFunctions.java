package sheron.csci4100u.labs.lab4;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


class UtilFunctions {

    private static final int NOTIFICATION_ID = 101;
    static String status;

    // Helper function to display notifications
    static void notifyStatus(Context context, String msg) {
        status = msg;

        if (!msg.equals("")) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            String title = getStringMessage(context, R.string.battery_status);

            // Add each battery msg on separate lines
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            for (String m : msg.split("\n")) {
                inboxStyle.addLine(m);
            }


            NotificationCompat.Builder nBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_battery)
                            .setContentTitle(title)
                            .setStyle(inboxStyle);

            NotificationManager nMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.notify(NOTIFICATION_ID, nBuilder.build());
        }
    }

    // Helper function to get status messages from strings file
    static String getStringMessage(Context context, int id) {
        return context.getResources().getString(id);
    }
}
