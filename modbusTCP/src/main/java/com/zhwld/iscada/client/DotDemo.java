package com.zhwld.iscada.client;

import java.util.ArrayList;
import java.util.List;

import com.zhwld.iscada.em.DotEnum;

import javafx.collections.ObservableList;

public class DotDemo {

	private String address;

	private String num;
	
	private String name;
	
	private String index;
	
	private String device;

	private List<DotEnum> listDetail;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index == null ? null : index.trim();
	}
	
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device == null ? null : device.trim();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public List<DotEnum> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<DotEnum> listDetail) {
		this.listDetail = listDetail;
	}
	
}
