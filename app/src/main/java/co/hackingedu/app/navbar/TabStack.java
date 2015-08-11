package co.hackingedu.app.navbar;

import java.util.ArrayList;

/**
 * Created by brandonkelley on 8/10/15.
 */
public class TabStack {

	private ArrayList<Integer> tabs;

	public TabStack() {
		tabs = new ArrayList<Integer>();
	}

	public void push(int tab) {
		tabs.add(tab);
	}

	public int pop() {
		int tab = tabs.get(tabs.size() - 1);
		tabs.remove(tabs.size() - 1);
		return tab;
	}

	public int peek() {
		return tabs.size() > 0 ? tabs.get(tabs.size() - 1) : -1;
	}

}
