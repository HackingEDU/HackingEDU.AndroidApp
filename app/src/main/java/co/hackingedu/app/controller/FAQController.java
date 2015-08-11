package co.hackingedu.app.controller;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;
import co.hackingedu.app.adapter.FAQAdapter;
import co.hackingedu.app.adapter.view.FAQ;

public class FAQController {

	private ArrayList<FAQ> FAQs;

	public FAQController() {
		FAQs = new ArrayList<FAQ>();
	}

	public void configure(Context context) {
		initialize(context.getResources());
		setListAdapter((HomeActivity) context);
	}

	private void initialize(Resources r) {

		FAQ help = new FAQ(r.getString(R.string.help_q), r.getString(R.string.help_a));
		FAQ first = new FAQ(r.getString(R.string.q1), r.getString(R.string.a1));
		FAQ second = new FAQ(r.getString(R.string.q2), r.getString(R.string.a2));
		FAQ third = new FAQ(r.getString(R.string.q3), r.getString(R.string.a3));
		FAQ fourth = new FAQ(r.getString(R.string.q4), r.getString(R.string.a4));

		FAQs.add(help);
		FAQs.add(first);
		FAQs.add(second);
		FAQs.add(third);
		FAQs.add(fourth);

	}

	private void setListAdapter(final HomeActivity activity) {
		FAQAdapter adapter = new FAQAdapter(activity, FAQs);
		ListView FAQlist = (ListView) activity.findViewById(R.id.faq_list);
		FAQlist.setAdapter(adapter);
		FAQlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (id == 0) {
					activity.inflateView(R.layout.fragment_home_faq_form);
					activity.findViewById(R.id.help_form_submit).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							View current = activity.getCurrentFocus();
							if (current != null) {
								InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
								manager.hideSoftInputFromWindow(current.getWindowToken(), 0);
							}
							activity.deflateView();
						}
					});
				}
			}
		});
	}

}
