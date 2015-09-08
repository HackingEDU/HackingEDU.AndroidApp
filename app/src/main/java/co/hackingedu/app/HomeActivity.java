package co.hackingedu.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;

import co.hackingedu.app.alert.AlertController;
import co.hackingedu.app.faq.FAQController;
import co.hackingedu.app.map.MapController;
import co.hackingedu.app.schedule.ScheduleController;
import co.hackingedu.app.navbar.Navbar;
import co.hackingedu.app.service.RegistrationService;

public class HomeActivity extends Activity {

	private static final int HOME_TAB = 0;
	private static final int MAP_TAB = 1;
	private static final int SCHEDULE_TAB = 2;
	private static final int FAQ_TAB = 3;
	private static final int ALERT_TAB = 4;

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
		populateMap();
		populateSchedule();
		populateFAQ();
		populateAlerts();

		if (checkPlayServices()) {
			Intent intent = new Intent(this, RegistrationService.class);
			startService(intent);
		}

	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
			} else {
				Log.d("BMK", "Google Play Failed.");
				finish();
			}
			return false;
		}
		return true;
	}

	private void setNavbar() {

		ViewGroup homeTab = (ViewGroup) findViewById(R.id.nav_home);
		ViewGroup mapTab = (ViewGroup) findViewById(R.id.nav_map);
		ViewGroup scheduleTab = (ViewGroup) findViewById(R.id.nav_calendar);
		ViewGroup faqTab = (ViewGroup) findViewById(R.id.nav_question);
		ViewGroup alertTab = (ViewGroup) findViewById(R.id.nav_alert);
		ViewGroup content = (ViewGroup) findViewById(R.id.content);

		nav = new Navbar(content, getLayoutInflater());

		nav.addTab(homeTab, R.layout.fragment_home_home);
		nav.addTab(mapTab, R.layout.fragment_home_map);
		nav.addTab(scheduleTab, R.layout.fragment_home_schedule);
		nav.addTab(faqTab, R.layout.fragment_home_faq);
		nav.addTab(alertTab, R.layout.fragment_home_alert);

		nav.attachListeners();

	}

	private void populateMap() {

		nav.setTab(MAP_TAB);

		MapController controller = new MapController();
		controller.configure(this);

		nav.resetTab();

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
