package com.example.indexsidebar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.indexsidebar.adapter.SelectLocationAdapter;
import com.example.indexsidebar.beans.MyLocation;
import com.example.indexsidebar.utils.LocationDAO;
import com.example.indexsidebar.views.SideBar;

public class MainActivity extends Activity {

	private List<MyLocation> locationAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		copyDB();
		
		initView();
	}

	private void initView() {
		SideBar location_sideBar = (SideBar) findViewById(R.id.location_sideBar);
		ListView location_listview = (ListView) findViewById(R.id.location_listview);
		locationAll = LocationDAO.findAll(this);
		
		if(locationAll!=null){
			SelectLocationAdapter locationAdapter = new SelectLocationAdapter(locationAll, this);
			locationAdapter.setSideBar(location_sideBar);
			locationAdapter.setListView(location_listview);
			location_listview.setAdapter(locationAdapter);
		}
	}

	/**
	 * 把china_city_name.db这个数据库拷贝到data/data/《包名》/files/china_city_name.db
	 */
	private void copyDB() {
		InputStream is = null;
		FileOutputStream fos = null;
		// 只要你拷贝了一次，我就不要你再拷贝了
		try {
			File file = new File(getFilesDir(), "china_city_name.db");
			if (file.exists() && file.length() > 0) {
				// 正常了，就不需要拷贝了
			} else {
				is = getAssets().open("china_city_name.db");

				fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (Exception e) {

			}
		}
	}
}
