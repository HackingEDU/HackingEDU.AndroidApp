package co.hackingedu.app.controller;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;
import co.hackingedu.app.adapter.ScheduleAdapter;
import co.hackingedu.app.adapter.view.Schedule;

public class ScheduleController implements Controller {

	private ArrayList<Schedule> schedules;

	public ScheduleController() {
		schedules = new ArrayList<Schedule>();
	}

	public void configure(Context context) {
		initialize(context.getResources());
		setListAdapter((HomeActivity) context);
	}

	private void initialize(Resources r) {

		Schedule s1 = new Schedule();
		s1.setName(r.getString(R.string.s1_name));
		s1.setLocation(r.getString(R.string.s1_location));
		s1.setStartTime(r.getString(R.string.s1_start_time));
		s1.setEndTime(r.getString(R.string.s1_end_time));
		s1.setSpeaker(r.getString(R.string.s1_speaker));
		s1.setDescription(r.getString(R.string.s1_description));
		s1.setImage(R.drawable.test);

		Schedule s2 = new Schedule();
		s2.setName(r.getString(R.string.s2_name));
		s2.setLocation(r.getString(R.string.s2_location));
		s2.setStartTime(r.getString(R.string.s2_start_time));
		s2.setEndTime(r.getString(R.string.s2_end_time));
		s2.setSpeaker(r.getString(R.string.s2_speaker));
		s2.setDescription(r.getString(R.string.s2_description));
		s2.setImage(R.drawable.test);

		Schedule s3 = new Schedule();
		s3.setName(r.getString(R.string.s3_name));
		s3.setLocation(r.getString(R.string.s3_location));
		s3.setStartTime(r.getString(R.string.s3_start_time));
		s3.setEndTime(r.getString(R.string.s3_end_time));
		s3.setSpeaker(r.getString(R.string.s3_speaker));
		s3.setDescription(r.getString(R.string.s3_description));
		s3.setImage(R.drawable.test);

		Schedule s4 = new Schedule();
		s4.setName(r.getString(R.string.s4_name));
		s4.setLocation(r.getString(R.string.s4_location));
		s4.setStartTime(r.getString(R.string.s4_start_time));
		s4.setEndTime(r.getString(R.string.s4_end_time));
		s4.setSpeaker(r.getString(R.string.s4_speaker));
		s4.setDescription(r.getString(R.string.s4_description));
		s4.setImage(R.drawable.test);

		schedules.add(s1);
		schedules.add(s2);
		schedules.add(s3);
		schedules.add(s4);

	}

	private void setListAdapter(final HomeActivity activity) {

		ScheduleAdapter adapter = new ScheduleAdapter(activity, schedules);
		ListView scheduleView = (ListView) activity.findViewById(R.id.schedule_list);
		scheduleView.setAdapter(adapter);

		scheduleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				final Schedule clicked = schedules.get(position);

				activity.inflateView(R.layout.fragment_home_schedule_item);
				((TextView) activity.findViewById(R.id.schedule_item_title)).setText(clicked.getName());
				((TextView) activity.findViewById(R.id.schedule_item_details)).setText(clicked.getDetails());
				((ImageView) activity.findViewById(R.id.schedule_item_image)).setImageResource(clicked.getImage());
				((TextView) activity.findViewById(R.id.schedule_item_speaker)).setText(clicked.getSpeaker());
				((TextView) activity.findViewById(R.id.schedule_item_description)).setText(clicked.getDescription());

			}

		});

	}

}
