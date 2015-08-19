package co.hackingedu.app.map;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.HomeActivity;
import co.hackingedu.app.R;

public class MapController {

	private ArrayList<Map> maps;

	public MapController() {
		maps = new ArrayList<Map>();
	}

	public void configure(Context context) {
		initialize(context.getResources());
		setListAdapter((HomeActivity) context);
	}

	private void initialize(Resources r) {

		Map m1 = new Map("Map 1", R.drawable.event_map, R.drawable.event_map_thumb);
		Map m2 = new Map("Map 2", R.drawable.event_map, R.drawable.event_map_thumb);
		Map m3 = new Map("Map 3", R.drawable.event_map, R.drawable.event_map_thumb);

		maps.add(m1);
		maps.add(m2);
		maps.add(m3);

	}

	private void setListAdapter(final HomeActivity activity) {

		MapAdapter adapter = new MapAdapter(activity, maps);
		ListView mapView = (ListView) activity.findViewById(R.id.map_list);
		mapView.setAdapter(adapter);

		mapView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				final Map clicked = maps.get(position);

				activity.inflateView(R.layout.fragment_home_map_item);
				((TextView) activity.findViewById(R.id.map_full_title)).setText(clicked.getTitle());
				((ImageView) activity.findViewById(R.id.map_full_image)).setImageResource(clicked.getImage());

			}
		});

	}

}
