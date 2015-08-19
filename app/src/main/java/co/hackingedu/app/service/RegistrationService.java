package co.hackingedu.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import co.hackingedu.app.R;

public class RegistrationService extends IntentService {

	private static final String NAME = "REGISTRATION_SERVICE";

	private String token;

	public RegistrationService() {
		super(NAME);
		token = "";
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			InstanceID instanceID = InstanceID.getInstance(this);
			String senderId = getString(R.string.sender_id);
			token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
		} catch (IOException e) {
			Log.d("BMK", "Unable to get token.");
		}

		updateServer();

	}

	private void updateServer() {
		new UpdateServerTask().execute();
	}

	private class UpdateServerTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... args) {

			HttpURLConnection connection;
			OutputStreamWriter request;
			URL url;
			String body = "token=" + token;

			try {

				url = new URL("http://52.27.55.98/addDevice");
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestMethod("POST");

				request = new OutputStreamWriter(connection.getOutputStream());
				request.write(body);
				request.flush();
				request.close();

				connection.getResponseCode();
				Log.d("BMK", "Updated token successfully.");

			} catch (IOException ex) {

				Log.d("BMK", ex.toString());

			}

			return 0;

		}

	}

}
