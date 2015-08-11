package co.hackingedu.app.navbar;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;

public class Navbar {

	private ArrayList<ViewGroup> tabs;
	private ArrayList<View> contents;
	private TabStack previousTabs;
	private LayoutInflater inflater;
	private ViewGroup contentFrame;
	private int currentTabIndex;
	private Resources resources;
	private View tempInflated;

	public Navbar(ViewGroup parent, LayoutInflater layoutInflater) {
		tabs = new ArrayList<ViewGroup>();
		contents = new ArrayList<View>();
		previousTabs = new TabStack();
		inflater = layoutInflater;
		contentFrame = parent;
		currentTabIndex = -1;
		resources = parent.getResources();
	}

	public void addTab(ViewGroup tab, int layoutId) {

		tabs.add(tab);
		contents.add(inflater.inflate(layoutId, null));

		if (currentTabIndex == -1) {
			contentFrame.addView(contents.get(0));
			tabs.get(0).getChildAt(1).setBackgroundColor(resources.getColor(R.color.white));
			currentTabIndex = 0;
		}

	}

	public void setTab(int index) {
		contentFrame.removeView(contents.get(currentTabIndex));
		contentFrame.addView(contents.get(index));
		previousTabs.push(currentTabIndex);
		currentTabIndex = index;
	}

	public void resetTab() {
		contentFrame.removeView(contents.get(currentTabIndex));
		contentFrame.addView(contents.get(0));
		currentTabIndex = previousTabs.pop();
	}

	public void inflateView(int layoutId) {
		tempInflated = inflater.inflate(layoutId, null);
		contentFrame.removeView(contents.get(currentTabIndex));
		contentFrame.addView(tempInflated);
		previousTabs.push(currentTabIndex);
	}

	public void deflateView() {
		if (tempInflated != null) {
			contentFrame.removeView(tempInflated);
			contentFrame.addView(contents.get(previousTabs.pop()));
			tempInflated = null;
		}
	}

	public boolean hasInflated() {
		if (tempInflated == null) {
			return false;
		}
		return true;
	}

	public int getPreviousTab() {
		return previousTabs.peek();
	}

	public void setPreviousTab() {
		int previousTab = previousTabs.pop();
		transitionTab(currentTabIndex, previousTab);
		currentTabIndex = previousTab;
	}

	public void attachListeners() {

		for (int i = 0; i < tabs.size(); i++) {
			final int j = i;
			tabs.get(j).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (tempInflated != null) {
						deflateView();
						tempInflated = null;
					}
					if (currentTabIndex != j) {
						transitionTab(currentTabIndex, j);
						previousTabs.push(currentTabIndex);
						currentTabIndex = j;
					}
				}
			});
		}

	}

	private void transitionTab(int oldTab, int newTab) {
		contentFrame.removeView(contents.get(oldTab));
		contentFrame.addView(contents.get(newTab));
		tabs.get(oldTab).getChildAt(1).setBackgroundColor(resources.getColor(R.color.grey));
		tabs.get(newTab).getChildAt(1).setBackgroundColor(resources.getColor(R.color.white));
		ImageView alertThumb = (ImageView) tabs.get(3).getChildAt(0);
		if (newTab == 3) {
			alertThumb.setImageResource(R.drawable.alert);
			((HomeActivity) contentFrame.getContext()).setAlerts(false);
		} else {
			if (((HomeActivity) contentFrame.getContext()).hasAlerts()) {
				alertThumb.setImageResource(R.drawable.newalert);
			} else {
				alertThumb.setImageResource(R.drawable.alert);
			}
		}
	}

}
