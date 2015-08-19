package co.hackingedu.app.alert;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ListView;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;
import co.hackingedu.app.alert.AlertAdapter;

public class AlertController {

	private ArrayList<String> alerts;

	public AlertController() {
		alerts = new ArrayList<String>();
	}

	public void setAlerts(ArrayList<String> alerts) {
		this.alerts = alerts;
	}

	public void configure(Context context) {
		//initialize(context.getResources());
		setListAdapter((HomeActivity) context);
	}

	private void initialize(Resources r) {
		alerts.add(r.getString(R.string.alert_pizza));
		alerts.add(r.getString(R.string.alert_time));
		alerts.add(r.getString(R.string.alert_git));
	}

	private void setListAdapter(HomeActivity activity) {
		AlertAdapter adapter = new AlertAdapter(activity, alerts);
		ListView alertView = (ListView) activity.findViewById(R.id.alert_list);
		alertView.setAdapter(adapter);
	}

}
