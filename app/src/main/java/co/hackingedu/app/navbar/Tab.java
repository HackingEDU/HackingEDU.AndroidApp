package co.hackingedu.app.navbar;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import co.hackingedu.app.R;

class Tab {

	private int id;
	private ViewGroup trigger;
	private View content;
	private int white;
	private int grey;

	public Tab(int id, ViewGroup trigger, View content) {
		this.id = id;
		this.trigger = trigger;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setContext(Context context) {
		Resources r = context.getResources();
		white = r.getColor(R.color.white);
		grey = r.getColor(R.color.grey);
	}

	public ViewGroup getTrigger() {
		return trigger;
	}

	public View getContent() {
		return content;
	}

	public void setActive() {
		trigger.getChildAt(1).setBackgroundColor(white);
	}

	public void setInactive() {
		trigger.getChildAt(1).setBackgroundColor(grey);
	}

}
