package com.example.indexsidebar.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.indexsidebar.R;
import com.example.indexsidebar.beans.MyLocation;
import com.example.indexsidebar.beans.RankLocation;
import com.example.indexsidebar.utils.FriendRankUtil;
import com.example.indexsidebar.views.SideBar;
import com.example.indexsidebar.views.SideBar.OnTouchingLetterChangedListener;
/**
 * 选择城市
 * @author Administrator
 *
 */
public class SelectLocationAdapter extends BaseAdapter implements OnTouchingLetterChangedListener {

	List<RankLocation> locations = new ArrayList<RankLocation>();//排序后
	List<MyLocation> list = new ArrayList<MyLocation>();//排序前
	Map<String, Integer> map = new HashMap<String, Integer>();
	ListView listView;
	SideBar sideBar;
	Context context;
	
	protected LayoutInflater inflater;
	private Handler handler;

	public SelectLocationAdapter(List<MyLocation> list, Context context) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		handler = new Handler();
		// 排序
		new Thread() {
			@Override
			public void run() {
				map.clear();
				locations = FriendRankUtil.toRankLocation(SelectLocationAdapter.this.list, map);
				handler.post(new Runnable() {
					@Override
					public void run() {
						notifyDataSetChanged();
					}
				});
			}
		}.start();
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return locations.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RankLocation rank = locations.get(position);
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.location_item, null);
			holder.location_item_title = (LinearLayout) convertView.findViewById(R.id.location_item_title);
			holder.location_item_letter = (TextView) convertView.findViewById(R.id.location_item_letter);
			holder.location_item_name = (TextView) convertView.findViewById(R.id.location_item_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (rank.isFirst) {
			holder.location_item_title.setVisibility(View.VISIBLE);
			holder.location_item_letter.setText(locations.get(position).index);
		} else {
			holder.location_item_title.setVisibility(View.GONE);
		}
		
		holder.location_item_name.setText(rank.location.getCity());

		return convertView;
	}

	public class Holder {
		LinearLayout location_item_title;// 分组
		TextView location_item_letter;// 分组字母
		TextView location_item_name;// 地址
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public void setSideBar(SideBar sideBar) {
		this.sideBar = sideBar;
		sideBar.setOnTouchingLetterChangedListener(this);
	}

	@Override
	public void onTouchingLetterChanged(String s) {
		if (listView != null && map.get(s) != null) {
			listView.setSelection(map.get(s));
		}
	}
	
	@Override
	public void notifyDataSetChanged() {
		// 排序
		map.clear();
		locations = FriendRankUtil.toRankLocation(list, map);
		super.notifyDataSetChanged();
	}

	public List<RankLocation> getLocations() {
		return locations;
	}
	
}
