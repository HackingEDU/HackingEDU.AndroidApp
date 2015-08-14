package co.hackingedu.app.service;

import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import co.hackingedu.app.R;

public class InstanceIdListenerService extends InstanceIDListenerService {

	private Runnable getToken;
	private Runnable postToken;
	private Runnable postNewToken;
	private Thread getTokenThread;
	private Thread postTokenThread;
	private Thread postNewTokenThread;
	private InstanceID instanceId;
	private String senderId;
	private String token;
	private String newToken;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		setRunnables();

		getTokenThread = new Thread(getToken);
		postTokenThread = new Thread(postToken);
		postNewTokenThread = new Thread(postNewToken);

		instanceId = InstanceID.getInstance(this);
		senderId = getString(R.string.sender_id);

		getTokenThread.start();
		token = newToken;
		newToken = "";
		postTokenThread.start();

		return START_STICKY;

	}

	@Override
	public void onDestroy() {
		token = "";
	}

	@Override
	public void onTokenRefresh() {

		getTokenThread.start();
		postNewTokenThread.start();

	}

	private void setRunnables() {

		getToken = new Runnable() {
			@Override
			public void run() {
				String retrievedToken;

				try {
					retrievedToken = instanceId.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
				} catch (IOException e) {
					retrievedToken = "";
					e.printStackTrace();
				}

				newToken = retrievedToken;
			}
		};

		postToken = new Runnable() {
			@Override
			public void run() {

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

				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		};

		postNewToken = new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection;
				OutputStreamWriter request;
				URL url;
				String body = "oldToken=" + token + "&newToken=" + newToken;

				try {

					url = new URL("http://52.27.55.98/updateDevice");
					connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					connection.setRequestMethod("POST");

					request = new OutputStreamWriter(connection.getOutputStream());
					request.write(body);
					request.flush();
					request.close();

					token = newToken;
					newToken = "";

				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		};

	}

}
