package co.hackingedu.app.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.hackingedu.app.R;

class MapAdapter extends BaseAdapter {

	private ArrayList<Map> maps;
	private LayoutInflater inflater;

	public MapAdapter(Context context, ArrayList<Map> maps) {
		this.maps = maps;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return maps.size();
	}

	@Override
	public Map getItem(int index) {
		return maps.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {

		ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_home_map_view, null);
			holder = new ViewHolder();
			holder.thumb = (ImageView) view.findViewById(R.id.map_thumb);
			holder.title = (TextView) view.findViewById(R.id.map_title);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		Map map = maps.get(index);
		holder.thumb.setImageResource(map.getImageThumb());
		holder.title.setText(map.getTitle());

		return view;

	}

	private class ViewHolder {
		ImageView thumb;
		TextView title;
	}

}
