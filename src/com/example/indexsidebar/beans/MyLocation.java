package com.example.indexsidebar.beans;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="wz_district")
public class MyLocation implements Serializable{
	@Id
	@Column(column = "dist_id")
	int dist_id;
	@Column(column = "parent_id")
	int parent_id;
	@Column(column = "province")
	String province;
	@Column(column = "city")
	String city;
	
	double latitude;
	double longitude;
	String name;
	
//	@Finder(valueColumn = "dist_id", targetColumn = "parent_id")
//	Location parent;

	public int getDist_id() {
		return dist_id;
	}

	public void setDist_id(int dist_id) {
		this.dist_id = dist_id;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Location getParent() {
//		return parent;
//	}
//
//	public void setParent(Location parent) {
//		this.parent = parent;
//	}
	
	
}
