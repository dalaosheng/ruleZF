package com.zhwld.iscada.client;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;

public class MqttRule {
	private TextField mqttParamValue;
	private String mqttParamType;
	
	public TextField getMqttParamValue() {
		return mqttParamValue;
	}

	public void setMqttParamValue(TextField mqttParamValue) {
		this.mqttParamValue = mqttParamValue;
	}
	
	public String getMqttParamType() {
		return mqttParamType;
	}

	public void setMqttParamType(String mqttParamType) {
		this.mqttParamType = mqttParamType;
	}
	
	public static List<MqttRule> getDataList() {

		List<MqttRule> saveList = new ArrayList<MqttRule>();
		for(int i=0;i<=7;i++) {
			MqttRule br = new MqttRule();
			TextField tf = new TextField();
			tf.setEditable(true);
			tf.setMaxWidth(200);
			if(i==0) {
				tf.setText("127.0.0.1");
				br.setMqttParamValue(tf);
				br.setMqttParamType("MQTT的地址或域名");
			}else if(i==1){
				tf.setText("1883");
				br.setMqttParamValue(tf);
				br.setMqttParamType("MQTT服务器的端口号");
			}else if(i==2){
				tf.setText("user");
				br.setMqttParamValue(tf);
				br.setMqttParamType("MQTT的登陆账号");
			}else if(i==3){
				tf.setText("password");
				br.setMqttParamValue(tf);
				br.setMqttParamType("MQTT的登陆密码");
			}else if(i==4){
				tf.setText("600");
				br.setMqttParamValue(tf);
				br.setMqttParamType("MQTT心跳包的间隔");
			}else if(i==5){
				tf.setText("mqttjs_23d3c018c1");
				br.setMqttParamValue(tf);
				br.setMqttParamType("MQTT客户端ID");
			}else if(i==6){
				tf.setText("/s/test/");
				br.setMqttParamValue(tf);
				br.setMqttParamType("订阅消息主题");
			}else if(i==7){
				tf.setText("/d/test/");
				br.setMqttParamValue(tf);
				br.setMqttParamType("发布消息主题");
			}

			saveList.add(br);
		}
		return saveList;
	}
}
