package co.hackingedu.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends Activity {

	private static final int MAP_TAB = 0;
	private static final int CAL_TAB = 1;
	private static final int FAQ_TAB = 2;
	private static final int ALERT_TAB = 3;

	private Navbar nav;
	private Resources r;
	private int previousTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		r = getResources();
		previousTab = -1;

		setNavbar();
		populateFAQ();

	}

	private void setNavbar() {

		ViewGroup map = (ViewGroup) findViewById(R.id.nav_map);
		ViewGroup cal = (ViewGroup) findViewById(R.id.nav_calendar);
		ViewGroup quest = (ViewGroup) findViewById(R.id.nav_question);
		ViewGroup alert = (ViewGroup) findViewById(R.id.nav_alert);
		ViewGroup content = (ViewGroup) findViewById(R.id.content);

		nav = new Navbar(content, getLayoutInflater());

		nav.addTab(map, R.layout.fragment_home_map);
		nav.addTab(cal, R.layout.fragment_home_calendar);
		nav.addTab(quest, R.layout.fragment_home_question);
		nav.addTab(alert, R.layout.fragment_home_alert);

		nav.attachListeners();

	}

	private void populateFAQ() {

		nav.setTab(FAQ_TAB);

		FAQ first = new FAQ(r.getString(R.string.q1), r.getString(R.string.a1));
		FAQ second = new FAQ(r.getString(R.string.q2), r.getString(R.string.a2));
		FAQ third = new FAQ(r.getString(R.string.q3), r.getString(R.string.a3));
		FAQ fourth = new FAQ(r.getString(R.string.q4), r.getString(R.string.a4));
		FAQ fifth = new FAQ(r.getString(R.string.q5), r.getString(R.string.a5));

		ArrayList<FAQ> FAQs = new ArrayList<FAQ>();
		FAQs.add(first);
		FAQs.add(second);
		FAQs.add(third);
		FAQs.add(fourth);
		FAQs.add(fifth);

		FAQAdapter adapter = new FAQAdapter(this, FAQs);
		ListView FAQlist = (ListView) findViewById(R.id.faq_list);

		FAQlist.setAdapter(adapter);
		FAQlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (id == 4) {
					nav.inflateView(R.layout.fragment_home_question_form);
					findViewById(R.id.help_form_submit).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							View current = getWindow().getCurrentFocus();
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

	@Override
	public void onBackPressed() {
		if (previousTab == -1) {
			super.onBackPressed();
		} else if (nav.hasInflated()) {
			nav.deflateView();
		} else {
			int temp = nav.getTab();
			nav.setTab(previousTab);
			previousTab = temp;
		}
	}

}
