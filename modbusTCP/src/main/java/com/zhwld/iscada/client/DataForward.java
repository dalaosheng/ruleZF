package com.zhwld.iscada.client;

import com.zhwld.iscada.em.FrameEnum;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class DataForward {
	private String address;

	private String name;
	
	private String num;
	
	private String device;
	
	private String index;
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

