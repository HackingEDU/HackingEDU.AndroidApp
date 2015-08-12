package co.hackingedu.app.scroll;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import co.hackingedu.app.R;

public class ScrollManager {

	private float mx, my;
	private float curX, curY;

	private ScrollView vScroll;
	private HorizontalScrollView hScroll;

	public ScrollManager(Context context) {
		vScroll = (ScrollView) ((Activity) context).findViewById(R.id.vScroll);
		hScroll = (HorizontalScrollView) ((Activity) context).findViewById(R.id.hScroll);
		vScroll.setVerticalScrollBarEnabled(false);
		hScroll.setHorizontalScrollBarEnabled(false);
	}

	public boolean processScroll(MotionEvent event) {

		switch(event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				mx = event.getX();
				my = event.getY();
				break;

			case MotionEvent.ACTION_MOVE:
				curX = event.getX();
				curY = event.getY();
				vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
				hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
				mx = curX;
				my = curY;
				break;

			case MotionEvent.ACTION_UP:
				curX = event.getX();
				curY = event.getY();
				vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
				hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
				break;

		}

		return true;

	}

}
