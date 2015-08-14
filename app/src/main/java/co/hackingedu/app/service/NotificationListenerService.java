package co.hackingedu.app.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

public class NotificationListenerService extends GcmListenerService {

	@Override
	public void onMessageReceived(String from, Bundle bundle) {
		String message = bundle.getString("message");
		System.out.println(from);
		System.out.println(message);
		// Do something with notification
	}

}
