package co.hackingedu.app.navbar;

import java.util.ArrayList;

public class TabStack {

	private ArrayList<Tab> tabs;

	public TabStack() {
		tabs = new ArrayList<Tab>();
	}

	public void push(Tab tab) {
		tabs.add(tab);
	}

	public Tab pop() {
		Tab tab = tabs.get(tabs.size() - 1);
		tabs.remove(tabs.size() - 1);
		return tab;
	}

	public int peek() {
		return tabs.size() > 0 ? tabs.get(tabs.size() - 1).getId() : -1;
	}

}
