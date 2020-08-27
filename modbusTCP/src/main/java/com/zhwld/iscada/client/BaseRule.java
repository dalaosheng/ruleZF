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

import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class BaseRule {
	private ComboBox paramValue;
	private String paramType;
	
	public ComboBox getParamValue() {
		return paramValue;
	}

	public void setParamValue(ComboBox paramValue) {
		this.paramValue = paramValue;
	}
	
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	public static List<BaseRule> getDataList() {

		List<BaseRule> saveList = new ArrayList<BaseRule>();
		for(int i=0;i<=3;i++) {
			BaseRule br = new BaseRule();
			ComboBox cbb = new ComboBox();
			cbb.setEditable(true);
			cbb.setMaxWidth(200);
			if(i==0) {
				cbb.setValue("183.63.14.187");
				br.setParamValue(cbb);
				br.setParamType("主站ip地址");
			}else if(i==1){
				cbb.setValue(2404);
				br.setParamValue(cbb);
				br.setParamType("端口号");
			}else if(i==2) {
				cbb.setValue(1);
				br.setParamValue(cbb);
				br.setParamType("设备地址(H)");
			}
//			}else if(i==2) {
//				cbb.getItems().addAll(
//		            "不校时",
//		            "接收校时",
//		            "发送校时"
//		        );
//				br.setParamValue(cbb);
//				br.setParamType("校时方式");
//			}else if(i==3){
//				cbb.setValue(30);
//				br.setParamValue(cbb);
//				br.setParamType("校时周期(分钟)");
//			}else if(i==4){
//				cbb.setValue(30);
//				br.setParamValue(cbb);
//				br.setParamType("电度召唤(分钟)");
//			}else if(i==5){
//				cbb.setValue(300000);
//				br.setParamValue(cbb);
//				br.setParamType("全数据召唤(ms)");
//			}else if(i==6){
//				cbb.setValue(1000);
//				br.setParamValue(cbb);
//				br.setParamType("变化数据召唤(ms)");
//			}else if(i==7){
//				cbb.setValue(500);
//				br.setParamValue(cbb);
//				br.setParamType("链路应答召唤(ms)");
//			}else if(i==8){
//				cbb.setValue(10000);
//				br.setParamValue(cbb);
//				br.setParamType("操作超时(ms)");
//			}else if(i==9){
//				cbb.setValue(50);
//				br.setParamValue(cbb);
//				br.setParamType("最小发送间隔(ms)");
//			}

			cbb.getSelectionModel().select(0);
			saveList.add(br);
		}
		return saveList;
	}
	
}
