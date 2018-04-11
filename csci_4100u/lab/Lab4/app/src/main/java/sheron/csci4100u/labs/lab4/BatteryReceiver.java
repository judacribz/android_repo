package sheron.csci4100u.labs.lab4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import static sheron.csci4100u.labs.lab4.UtilFunctions.*;


public class BatteryReceiver extends BroadcastReceiver {

    String last_status = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Notification output message constant setup
        // Battery status messages
        final String BATTERY_CHARGING = getStringMessage(context, R.string.battery_charging);
        final String BATTERY_DISCHARGING = getStringMessage(context, R.string.battery_discharging);
        final String BATTERY_FULL = getStringMessage(context, R.string.battery_full);

        // Battery charge type messages
        final String BATTERY_PLUGGED_USB = getStringMessage(context, R.string.usb);
        final String BATTERY_PLUGGED_AC  = getStringMessage(context, R.string.ac);

        // Battery health messages
        final String BATTERY_GOOD = getStringMessage(context, R.string.battery_good);
        final String BATTERY_COLD = getStringMessage(context, R.string.battery_cold);
        final String BATTERY_DEAD = getStringMessage(context, R.string.battery_dead);
        final String BATTERY_OVER_VOLTAGE = getStringMessage(context,R.string.battery_over_voltage);
        final String BATTERY_OVERHEAT = getStringMessage(context, R.string.battery_overheat);
        final String BATTERY_HEALTH_UNKNOWN = getStringMessage(context,
                                                               R.string.battery_health_unknown);
        final String BATTERY_UNSP_FAILURE = getStringMessage(context,
                                                             R.string.battery_unsp_failure);

        // Temperature message
        final String BATTERY_TEMPERATURE_IS = getStringMessage(context,
                                                               R.string.battery_temperature_is);

        String status = "";

        // Get battery charging state
        int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        switch (batteryStatus) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                status += BATTERY_CHARGING;
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                status += BATTERY_DISCHARGING;
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                status += BATTERY_FULL;
                break;
        }

        // Get battery charging type
        if (batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
            int batteryPlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            switch (batteryPlug) {
                case BatteryManager.BATTERY_PLUGGED_USB:
                    status += BATTERY_PLUGGED_USB;
                    break;
                case BatteryManager.BATTERY_PLUGGED_AC:
                    status += BATTERY_PLUGGED_AC;
                    break;
            }
        }

        // Get battery health
        int batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        switch (batteryHealth) {
            case BatteryManager.BATTERY_HEALTH_GOOD:
                status += BATTERY_GOOD;
                break;
            case BatteryManager.BATTERY_HEALTH_COLD:
                status += BATTERY_COLD;
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                status += BATTERY_DEAD;
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                status += BATTERY_OVER_VOLTAGE;
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                status += BATTERY_OVERHEAT;
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                status += BATTERY_HEALTH_UNKNOWN;
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                status += BATTERY_UNSP_FAILURE;
                break;
        }

        // Get battery temperature
        int batteryTemp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        if (batteryTemp != -1) {
            status += BATTERY_TEMPERATURE_IS + " " + (float) batteryTemp/10 + " °C";
        }

        // Only updates notification if status changes
        if (!status.equals(last_status)) {
            // Send notification
            last_status = status;

            notifyStatus(context, status);
        }
    }
}
