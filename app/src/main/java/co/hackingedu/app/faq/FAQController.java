package co.hackingedu.app.faq;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;

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
							String teamNumber = ((EditText) activity.findViewById(R.id.help_form_team)).getText().toString();
							String question = ((EditText) activity.findViewById(R.id.help_form_question)).getText().toString();
							processHelpForm(activity, teamNumber, question);
						}
					});
				}
			}
		});
	}

	private void processHelpForm(HomeActivity activity, String teamNumber, String question) {

		if (teamNumber.equals("")) {
			String message = "Please enter a valid team number.";
			int length = Toast.LENGTH_SHORT;
			Toast.makeText(activity, message, length).show();
			return;
		}

		if (question.equals("")) {
			String message = "Please enter a question.";
			int length = Toast.LENGTH_SHORT;
			Toast.makeText(activity, message, length).show();
			return;
		}

		activity.deflateView();

		String message = "Request sent successfully.";
		int length = Toast.LENGTH_SHORT;
		Toast.makeText(activity, message, length).show();
		// Do something with information

	}

}
