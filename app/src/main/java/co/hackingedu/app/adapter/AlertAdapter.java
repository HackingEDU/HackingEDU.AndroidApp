package co.hackingedu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.R;

public class AlertAdapter extends BaseAdapter {

	private ArrayList<String> alerts;
	private LayoutInflater inflater;

	public AlertAdapter(Context context, ArrayList<String> alerts) {
		inflater = LayoutInflater.from(context);
		this.alerts = alerts;
	}

	@Override
	public int getCount() {
		return alerts.size();
	}

	@Override
	public String getItem(int index) {
		return alerts.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {

		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_home_alert_view, null);
			holder = new ViewHolder();
			holder.alert = (TextView) view.findViewById(R.id.alert);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		String alert = alerts.get(index);
		holder.alert.setText(alert);

		return view;

	}

	private static class ViewHolder {
		TextView alert;
	}

}
