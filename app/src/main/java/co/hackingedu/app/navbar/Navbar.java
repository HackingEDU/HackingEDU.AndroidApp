package co.hackingedu.app.navbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;

public class Navbar {

	public static final int ALERT_TAB = 4;

	private ArrayList<Tab> tabs;
	private TabStack previousTabs;
	private Tab currentTab;
	private int idCounter;
	private LayoutInflater inflater;
	private ViewGroup contentFrame;
	private View tempInflated;

	public Navbar(ViewGroup parent, LayoutInflater layoutInflater) {
		tabs = new ArrayList<Tab>();
		previousTabs = new TabStack();
		currentTab = null;
		idCounter = 0;
		inflater = layoutInflater;
		contentFrame = parent;
		tempInflated = null;
	}

	public void addTab(ViewGroup tabView, int layoutId) {

		Tab tab = new Tab(idCounter, tabView, inflater.inflate(layoutId, null));
		tab.setContext(contentFrame.getContext());
		tabs.add(tab);

		if (idCounter == 0) {
			contentFrame.addView(tab.getContent());
			tab.setActive();
			currentTab = tab;
		}

		idCounter++;

	}

	public void setTab(int index) {
		Tab newTab = tabs.get(index);
		contentFrame.removeView(currentTab.getContent());
		contentFrame.addView(newTab.getContent());
		previousTabs.push(currentTab);
		currentTab = newTab;
	}

	public void resetTab() {
		contentFrame.removeView(currentTab.getContent());
		contentFrame.addView(tabs.get(0).getContent());
		currentTab = previousTabs.pop();
	}

	public void inflateView(int layoutId) {
		tempInflated = inflater.inflate(layoutId, null);
		contentFrame.removeView(currentTab.getContent());
		contentFrame.addView(tempInflated);
		previousTabs.push(currentTab);
	}

	public void deflateView() {
		if (tempInflated != null) {
			contentFrame.removeView(tempInflated);
			contentFrame.addView(previousTabs.pop().getContent());
			tempInflated = null;
		}
	}

	public boolean hasInflated() {
		return tempInflated != null;
	}

	public int getCurrentTabIndex() {
		return currentTab.getId();
	}

	public int getPreviousTabIndex() {
		return previousTabs.peek();
	}

	public void setPreviousTab() {
		Tab previousTab = previousTabs.pop();
		transitionTab(currentTab, previousTab);
		currentTab = previousTab;
	}

	public void attachListeners() {

		for (int i = 0; i < tabs.size(); i++) {
			final int j = i;
			tabs.get(j).getTrigger().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (tempInflated != null) {
						deflateView();
						tempInflated = null;
					}
					if (currentTab.getId() != j) {
						transitionTab(currentTab, tabs.get(j));  // Really just calling
						previousTabs.push(currentTab);           // setTab(); :(
						currentTab = tabs.get(j);
					}
				}
			});
		}

	}

	private void transitionTab(Tab oldTab, Tab newTab) {

		contentFrame.removeView(oldTab.getContent());
		contentFrame.addView(newTab.getContent());
		oldTab.setInactive();
		newTab.setActive();

		ImageView alertThumb = (ImageView) tabs.get(ALERT_TAB).getTrigger().getChildAt(0);
		HomeActivity activity = ((HomeActivity) contentFrame.getContext());

		// Filter if alert tab should be active
		if (activity.hasAlerts()) {
			if (oldTab.getId() == ALERT_TAB) {
				activity.resetAlerts();
			} else if (newTab.getId() == ALERT_TAB) {
				alertThumb.setImageResource(R.drawable.alert);
			} else {
				alertThumb.setImageResource(R.drawable.newalert);
			}
		} else {
			alertThumb.setImageResource(R.drawable.alert);
		}

	}

}
