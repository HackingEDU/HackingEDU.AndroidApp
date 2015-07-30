package co.hackingedu.app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeActivity extends Activity {

	private ViewGroup map;
	private ViewGroup cal;
	private ViewGroup quest;
	private ViewGroup alert;
	private ViewGroup content;
	private ViewGroup currentSelected;

	private View inflatedMap;
	private View inflatedCal;
	private View inflatedQuest;
	private View inflatedAlert;
	private View currentInflated;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setViews();
		setLayouts();
		setNavListeners();
		inflateMapLayout();

	}

	private void setViews() {

		map = (ViewGroup) findViewById(R.id.nav_map);
		cal = (ViewGroup) findViewById(R.id.nav_calendar);
		quest = (ViewGroup) findViewById(R.id.nav_question);
		alert = (ViewGroup) findViewById(R.id.nav_alert);
		content = (ViewGroup) findViewById(R.id.content);

	}

	private void setLayouts() {

		LayoutInflater inflater = getLayoutInflater();
		inflatedMap = inflater.inflate(R.layout.fragment_home_map, null);
		inflatedCal = inflater.inflate(R.layout.fragment_home_calendar, null);
		inflatedQuest = inflater.inflate(R.layout.fragment_home_question, null);
		inflatedAlert = inflater.inflate(R.layout.fragment_home_alert, null);

	}

	private void setNavListeners() {

		map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentSelected != map) {

					content.removeView(currentInflated);
					content.addView(inflatedMap);

					Resources r = getResources();
					currentSelected.getChildAt(1).setBackgroundColor(r.getColor(R.color.grey));
					map.getChildAt(1).setBackgroundColor(r.getColor(R.color.white));

					currentSelected = map;
					currentInflated = inflatedMap;

				}
			}
		});

		cal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentSelected != cal) {

					content.removeView(currentInflated);
					content.addView(inflatedCal);

					Resources r = getResources();
					currentSelected.getChildAt(1).setBackgroundColor(r.getColor(R.color.grey));
					cal.getChildAt(1).setBackgroundColor(r.getColor(R.color.white));

					currentSelected = cal;
					currentInflated = inflatedCal;

				}
			}
		});

		quest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentSelected != quest) {

					content.removeView(currentInflated);
					content.addView(inflatedQuest);

					Resources r = getResources();
					currentSelected.getChildAt(1).setBackgroundColor(r.getColor(R.color.grey));
					quest.getChildAt(1).setBackgroundColor(r.getColor(R.color.white));

					currentSelected = quest;
					currentInflated = inflatedQuest;

				}
			}
		});

		alert.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentSelected != alert) {

					content.removeView(currentInflated);
					content.addView(inflatedAlert);

					Resources r = getResources();
					currentSelected.getChildAt(1).setBackgroundColor(r.getColor(R.color.grey));
					alert.getChildAt(1).setBackgroundColor(r.getColor(R.color.white));

					currentSelected = alert;
					currentInflated = inflatedAlert;

				}
			}
		});

	}

	private void inflateMapLayout() {

		content.addView(inflatedMap);
		map.getChildAt(1).setBackgroundColor(getResources().getColor(R.color.white));
		currentSelected = map;
		currentInflated = inflatedMap;

	}

}
