package co.hackingedu.app.faq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.R;

class FAQAdapter extends BaseAdapter {

	private ArrayList<FAQ> items;
	private LayoutInflater inflater;

	public FAQAdapter(Context context, ArrayList<FAQ> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public FAQ getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {

		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_home_faq_view, null);
			holder = new ViewHolder();
			holder.question = (TextView) view.findViewById(R.id.question_q);
			holder.answer = (TextView) view.findViewById(R.id.question_a);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		FAQ item = items.get(index);
		holder.question.setText(item.getQuestion());
		holder.answer.setText(item.getAnswer());

		return view;

	}

	private static class ViewHolder {
		TextView question;
		TextView answer;
	}

}
