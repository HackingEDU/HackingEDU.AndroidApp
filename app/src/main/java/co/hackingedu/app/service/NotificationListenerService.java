package co.hackingedu.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;

public class NotificationListenerService extends GcmListenerService {

	@Override
	public void onMessageReceived(String from, Bundle bundle) {

		Intent intent = new Intent(this, HomeActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification notif = new Notification.Builder(this)
				.setContentIntent(pIntent)
				.build();

		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(0, notif);

	}

}
