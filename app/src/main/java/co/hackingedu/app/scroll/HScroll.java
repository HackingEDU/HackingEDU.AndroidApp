package co.hackingedu.app.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class HScroll extends HorizontalScrollView {

	public HScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HScroll(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

}
