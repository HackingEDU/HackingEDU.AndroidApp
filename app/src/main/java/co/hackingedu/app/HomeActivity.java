package co.hackingedu.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.adapter.AlertAdapter;
import co.hackingedu.app.adapter.FAQAdapter;
import co.hackingedu.app.adapter.ScheduleAdapter;
import co.hackingedu.app.adapter.view.FAQ;
import co.hackingedu.app.adapter.view.Schedule;
import co.hackingedu.app.navbar.Navbar;

public class HomeActivity extends Activity {

	private static final int MAP_TAB = 0;
	private static final int SCHEDULE_TAB = 1;
	private static final int FAQ_TAB = 2;
	private static final int ALERT_TAB = 3;

	private Navbar nav;
	private Resources r;
	private boolean hasAlerts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		r = getResources();
		hasAlerts = false;

		setNavbar();
		populateSchedule();
		populateFAQ();
		populateAlerts();

	}

	private void setNavbar() {

		ViewGroup map = (ViewGroup) findViewById(R.id.nav_map);
		ViewGroup schedule = (ViewGroup) findViewById(R.id.nav_calendar);
		ViewGroup faq = (ViewGroup) findViewById(R.id.nav_question);
		ViewGroup alert = (ViewGroup) findViewById(R.id.nav_alert);
		ViewGroup content = (ViewGroup) findViewById(R.id.content);

		nav = new Navbar(content, getLayoutInflater());

		nav.addTab(map, R.layout.fragment_home_map);
		nav.addTab(schedule, R.layout.fragment_home_schedule);
		nav.addTab(faq, R.layout.fragment_home_faq);
		nav.addTab(alert, R.layout.fragment_home_alert);

		nav.attachListeners();

	}

	private void populateSchedule() {

		nav.setTab(SCHEDULE_TAB);

		ArrayList<Schedule> schedules = new ArrayList<Schedule>();

		Schedule s1 = new Schedule();
		s1.setName(r.getString(R.string.s1_name));
		s1.setLocation(r.getString(R.string.s1_location));
		s1.setStartTime(r.getString(R.string.s1_start_time));
		s1.setEndTime(r.getString(R.string.s1_end_time));
		s1.setImage(R.drawable.test);
		Schedule s2 = new Schedule();
		s2.setName(r.getString(R.string.s2_name));
		s2.setLocation(r.getString(R.string.s2_location));
		s2.setStartTime(r.getString(R.string.s2_start_time));
		s2.setEndTime(r.getString(R.string.s2_end_time));
		s2.setImage(R.drawable.test);
		Schedule s3 = new Schedule();
		s3.setName(r.getString(R.string.s3_name));
		s3.setLocation(r.getString(R.string.s3_location));
		s3.setStartTime(r.getString(R.string.s3_start_time));
		s3.setEndTime(r.getString(R.string.s3_end_time));
		s3.setImage(R.drawable.test);
		Schedule s4 = new Schedule();
		s4.setName(r.getString(R.string.s4_name));
		s4.setLocation(r.getString(R.string.s4_location));
		s4.setStartTime(r.getString(R.string.s4_start_time));
		s4.setEndTime(r.getString(R.string.s4_end_time));
		s4.setImage(R.drawable.test);

		schedules.add(s1);
		schedules.add(s2);
		schedules.add(s3);
		schedules.add(s4);

		ScheduleAdapter adapter = new ScheduleAdapter(this, schedules);
		ListView scheduleView = (ListView) findViewById(R.id.schedule_list);
		scheduleView.setAdapter(adapter);

		scheduleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String name = (String) ((TextView) parent.getChildAt(position).findViewById(R.id.schedule_name)).getText();
				String details = (String) ((TextView) parent.getChildAt(position).findViewById(R.id.schedule_details)).getText();
				Drawable image = ((ImageView) parent.getChildAt(position).findViewById(R.id.schedule_image)).getDrawable();

				nav.inflateView(R.layout.fragment_home_schedule_item);
				((TextView) findViewById(R.id.schedule_item_title)).setText(name);
				((TextView) findViewById(R.id.schedule_item_details)).setText(details);
				((ImageView) findViewById(R.id.schedule_item_image)).setImageDrawable(image);

			}

		});

		nav.resetTab();

	}

	private void populateFAQ() {

		nav.setTab(FAQ_TAB);

		FAQ help = new FAQ(r.getString(R.string.help_q), r.getString(R.string.help_a));
		FAQ first = new FAQ(r.getString(R.string.q1), r.getString(R.string.a1));
		FAQ second = new FAQ(r.getString(R.string.q2), r.getString(R.string.a2));
		FAQ third = new FAQ(r.getString(R.string.q3), r.getString(R.string.a3));
		FAQ fourth = new FAQ(r.getString(R.string.q4), r.getString(R.string.a4));

		ArrayList<FAQ> FAQs = new ArrayList<FAQ>();
		FAQs.add(help);
		FAQs.add(first);
		FAQs.add(second);
		FAQs.add(third);
		FAQs.add(fourth);

		FAQAdapter adapter = new FAQAdapter(this, FAQs);
		ListView FAQlist = (ListView) findViewById(R.id.faq_list);

		FAQlist.setAdapter(adapter);

		FAQlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (id == 0) {
					nav.inflateView(R.layout.fragment_home_faq_form);
					findViewById(R.id.help_form_submit).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							View current = getCurrentFocus();
							if (current != null) {
								InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
								manager.hideSoftInputFromWindow(current.getWindowToken(), 0);
							}
							nav.deflateView();
						}
					});
				}
			}
		});

		nav.resetTab();

	}

	private void populateAlerts() {

		nav.setTab(ALERT_TAB);

		ArrayList<String> alerts = new ArrayList<String>();
		alerts.add(r.getString(R.string.alert_pizza));
		alerts.add(r.getString(R.string.alert_time));
		alerts.add(r.getString(R.string.alert_git));

		AlertAdapter adapter = new AlertAdapter(this, alerts);
		ListView alertView = (ListView) findViewById(R.id.alert_list);
		alertView.setAdapter(adapter);

		nav.resetTab();

		hasAlerts = true;

	}

	public boolean hasAlerts() {
		return hasAlerts;
	}

	public void setAlerts(boolean hasAlerts) {
		this.hasAlerts = hasAlerts;
	}

	@Override
	public void onBackPressed() {
		if (nav.getPreviousTab() == -1) {
			super.onBackPressed();
		} else if (nav.hasInflated()) {
			nav.deflateView();
		} else {
			nav.setPreviousTab();
		}
	}

}
