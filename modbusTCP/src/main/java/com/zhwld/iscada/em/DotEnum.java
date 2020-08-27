package com.zhwld.iscada.em;


public enum DotEnum implements IEnum {
	
	YXConfig("遥信配置", 0),

	YCConfig("遥测配置", 1),

	MCConfig("脉冲配置", 2),

	YKConfig("遥控配置", 3),

	YTConfig("遥调配置", 4),
	
	DZConfig("定值配置", 5),

	TJDConfig("统计点配置", 6),
	
	ljjsConfig("逻辑计算配置", 7),
	
	MNJSConfig("模拟计算配置", 8);

	private String name;
	private int index;

	DotEnum(String name, int index) {
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
	
	public static DotEnum getByIndex(int index) {

		for (DotEnum dotEnum : DotEnum.values()) {
			if (dotEnum.getIndex() == index) {
				return dotEnum;
			}
		}
		return null;
	}
	
	public static int getByName(String name) {
		for (DotEnum dotEnum : DotEnum.values()) {

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
		for (DotEnum dotEnum : DotEnum.values()) {

			if (dotEnum.getName().equalsIgnoreCase(name)) {
				return dotEnum;
			}
		}
		return null;
	}
}

