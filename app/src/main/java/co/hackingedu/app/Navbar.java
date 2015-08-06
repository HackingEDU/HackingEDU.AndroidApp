package co.hackingedu.app;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Navbar {

	private ArrayList<ViewGroup> tabs;
	private ArrayList<View> contents;
	private LayoutInflater inflater;
	private ViewGroup contentFrame;
	private int currentTabIndex;
	private Resources resources;
	private View tempInflated;

	public Navbar(ViewGroup parent, LayoutInflater layoutInflater) {
		tabs = new ArrayList<ViewGroup>();
		contents = new ArrayList<View>();
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
		currentTabIndex = index;
	}

	public int getTab() {
		return currentTabIndex;
	}

	public void resetTab() {
		contentFrame.removeView(contents.get(currentTabIndex));
		contentFrame.addView(contents.get(0));
		currentTabIndex = 0;
	}

	public void inflateView(int layoutId) {
		tempInflated = inflater.inflate(layoutId, null);
		contentFrame.removeView(contents.get(currentTabIndex));
		contentFrame.addView(tempInflated);
	}

	public void deflateView() {
		if (tempInflated != null) {
			contentFrame.removeView(tempInflated);
			contentFrame.addView(contents.get(currentTabIndex));
			tempInflated = null;
		}
	}

	public boolean hasInflated() {
		if (tempInflated == null) {
			return false;
		}
		return true;
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
						contentFrame.removeView(contents.get(currentTabIndex));
						contentFrame.addView(contents.get(j));
						tabs.get(currentTabIndex).getChildAt(1).setBackgroundColor(resources.getColor(R.color.grey));
						tabs.get(j).getChildAt(1).setBackgroundColor(resources.getColor(R.color.white));
						currentTabIndex = j;
					}
				}
			});
		}

	}

}
