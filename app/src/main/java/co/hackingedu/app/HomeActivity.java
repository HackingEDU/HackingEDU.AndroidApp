package co.hackingedu.app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.hackingedu.app.controller.AlertController;
import co.hackingedu.app.controller.FAQController;
import co.hackingedu.app.controller.ScheduleController;
import co.hackingedu.app.navbar.Navbar;

public class HomeActivity extends Activity {

	private static final int MAP_TAB = 0;
	private static final int SCHEDULE_TAB = 1;
	private static final int FAQ_TAB = 2;
	private static final int ALERT_TAB = 3;

	private Navbar nav;
	private ArrayList<String> notifications;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		notifications = new ArrayList<String>();

		Resources r = getResources();
		notifications.add(r.getString(R.string.alert_pizza));
		notifications.add(r.getString(R.string.alert_time));
		notifications.add(r.getString(R.string.alert_git));

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

		ScheduleController controller = new ScheduleController();
		controller.configure(this);

		nav.resetTab();

	}

	private void populateFAQ() {

		nav.setTab(FAQ_TAB);

		FAQController controller = new FAQController();
		controller.configure(this);

		nav.resetTab();

	}

	private void populateAlerts() {

		nav.setTab(ALERT_TAB);

		AlertController controller = new AlertController();
		controller.setAlerts(notifications);
		controller.configure(this);

		nav.resetTab();

	}

	public void inflateView(int layoutId) {
		nav.inflateView(layoutId);
	}

	public void deflateView() {
		nav.deflateView();
	}

	public boolean hasAlerts() {
		return notifications.size() > 0;
	}

	public void resetAlerts() {
		notifications.clear();
	}

	@Override
	public void onBackPressed() {
		if (nav.getPreviousTabIndex() == -1) {
			super.onBackPressed();
		} else if (nav.hasInflated()) {
			nav.deflateView();
		} else {
			nav.setPreviousTab();
		}
	}

}
