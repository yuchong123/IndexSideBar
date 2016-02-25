package com.example.indexsidebar.utils;

import java.util.List;

import android.content.Context;

import com.example.indexsidebar.beans.MyLocation;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
/**
 * 全国城市数据库
 * @author Administrator
 *
 */
public class LocationDAO //extends OrmLiteSqliteOpenHelper 
{
	
//	public LocationDAO(Context context, String databaseName, CursorFactory factory, int databaseVersion) {
//		super(context, databaseName, factory, databaseVersion);
//		// TODO Auto-generated constructor stub
//	}

	private static String dbName="china_city_name.db";
	
	public static List<MyLocation> findAll(Context context){
		DbUtils dBUtils = DbUtils.create(context,context.getFilesDir().toString()+"/",dbName);
		List<MyLocation> all=null;
		try {
			all = dBUtils.findAll(Selector.from(MyLocation.class).where("parent_id","not" ,null));
//			all = dBUtils.findAll(Selector.from(Wz_district.class));
//			all = dBUtils.findAll(Wz_district.class);
//			Wz_district findById = dBUtils.findById(Wz_district.class, 2);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all;
	}
	
	public static MyLocation findById(Context context,int id){
		DbUtils dBUtils = DbUtils.create(context,context.getFilesDir().toString()+"/",dbName);
		MyLocation wz_district=null;
		try {
			wz_district = dBUtils.findById(MyLocation.class, id);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return wz_district;
	}
	
	public static MyLocation findByCityName(Context context,String city){
		DbUtils dBUtils = DbUtils.create(context,context.getFilesDir().toString()+"/",dbName);
		List<MyLocation> wz_district=null;
		try {
			wz_district = dBUtils.findAll(Selector.from(MyLocation.class).where("city", "like", city));
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (wz_district!=null&&wz_district.size()>0) {
			return wz_district.get(0);
		}
		return null;
	}
	
//	public static List<Wz_district> findAll(Context context){
//		List<Wz_district> all=null;
//		return all;
//	}
//	
//	public static Wz_district findById(Context context,int id){
//		Wz_district wz_district=null;
//		return wz_district;
//	}

//	@Override
//	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
//		// TODO Auto-generated method stub
//		
//	}
}
