package com.zhwld.iscada.em;


public enum DataForwardEnum implements IEnum {
	
	ZFYX("转发遥信配置", 0),

	ZFYC("转发遥测配置", 1),

	ZFMC("转发脉冲配置", 2),

	ZFYK("转发遥控配置", 3),

	ZFYT("转发遥调配置", 4),
	
	ZFDZ("转发定值配置", 5);

	private String name;
	private int index;

	DataForwardEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public static DataForwardEnum getByIndex(int index) {

		for (DataForwardEnum dotEnum : DataForwardEnum.values()) {
			if (dotEnum.getIndex() == index) {
				return dotEnum;
			}
		}
		return null;
	}
	
	public static int getByName(String name) {
		for (DataForwardEnum dotEnum : DataForwardEnum.values()) {

			if (dotEnum.getName().equalsIgnoreCase(name)) {
				return dotEnum.index;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

	public String getEnumName() {

		return getName();
	}

	@Override
	public IEnum getEnumByName(String name) {
		// TODO Auto-generated method stub
		for (DataForwardEnum dotEnum : DataForwardEnum.values()) {

			if (dotEnum.getName().equalsIgnoreCase(name)) {
				return dotEnum;
			}
		}
		return null;
	}
}

