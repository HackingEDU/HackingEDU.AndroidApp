package co.hackingedu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.R;
import co.hackingedu.app.adapter.view.Schedule;

public class ScheduleAdapter extends BaseAdapter {

	private ArrayList<Schedule> schedules;
	private LayoutInflater inflater;

	public ScheduleAdapter(Context context, ArrayList<Schedule> schedules) {
		this.schedules = schedules;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return schedules.size();
	}

	@Override
	public Schedule getItem(int index) {
		return schedules.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {

		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_home_schedule_view, null);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.schedule_image);
			holder.startTime = (TextView) view.findViewById(R.id.schedule_start_time);
			holder.name = (TextView) view.findViewById(R.id.schedule_name);
			holder.details = (TextView) view.findViewById(R.id.schedule_details);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Schedule schedule = schedules.get(index);
		holder.image.setImageResource(schedule.getImage());
		holder.startTime.setText(schedule.getStartTime());
		holder.name.setText(schedule.getName());
		holder.details.setText(schedule.getDetails());

		return view;

	}

	private static class ViewHolder {
		ImageView image;
		TextView startTime;
		TextView name;
		TextView details;
	}

}
