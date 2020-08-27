package com.zhwld.iscada.client;

import java.util.ArrayList;
import java.util.List;

public class BasicConfig {
	private String paramValue;
	private String paramType;
	
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	public static List<BasicConfig> getDataList() {
		List<BasicConfig> saveList = new ArrayList<BasicConfig>();
		for(int i=0;i<=7;i++) {
			BasicConfig bc = new BasicConfig();
			if(i==0) {
				bc.setParamType("名称");
				bc.setParamValue("默认通道");
			}else if(i==1){
				bc.setParamType("规约类型");
				bc.setParamValue("Modbus规约");
			}else if(i==2){
				bc.setParamType("规约参数");
				bc.setParamValue("规约参数");
			}else if(i==3){
				bc.setParamType("端口参数");
				bc.setParamValue("端口参数");
			}
			saveList.add(bc);
		}
		return saveList;		
	}
}
