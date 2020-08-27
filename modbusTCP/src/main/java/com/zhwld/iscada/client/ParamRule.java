package com.zhwld.iscada.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhwld.iscada.client.controller.IndexController;
import com.zhwld.iscada.em.FrameEnum;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParamRule {

	private TextField address;

	private String name;
	
	private ComboBox<FrameEnum> frame;
	
	private TextField code;

	private TextField data;

	public TextField getAddress() {
		return address;
	}

	public void setAddress(TextField address) {
		this.address = address;
	}

	public ComboBox<FrameEnum> getFrame() {
		return frame;
	}

	public void setFrame(ComboBox<FrameEnum> frame) {
		this.frame = frame;
	}
	
	public TextField getCode() {
		return code;
	}

	public void setCode(TextField code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TextField getData() {
		return data;
	}

	public void setData(TextField data) {
		this.data = data;
	}
	
	public static List<ParamRule> getAddList() {

		List<ParamRule> addList = new ArrayList<>();
		ParamRule paramDemo = new ParamRule();
		ObservableList<FrameEnum> listFrame = FXCollections.observableArrayList(FrameEnum.values());
		TextField textAddress = new TextField();
		textAddress.setText("000");
		TextField textCode = new TextField();
		textCode.setText("01");
		TextField textValue = new TextField();
		textValue.setText("01");
		
		paramDemo.setAddress(textAddress);
		paramDemo.setCode(textCode);
		ComboBox<FrameEnum> comboBox = new ComboBox();
		comboBox.setItems(listFrame);
		comboBox.getSelectionModel().selectFirst();
		paramDemo.setFrame(comboBox);
		paramDemo.setData(textValue);
		addList.add(paramDemo);
		return addList;
	}

}
