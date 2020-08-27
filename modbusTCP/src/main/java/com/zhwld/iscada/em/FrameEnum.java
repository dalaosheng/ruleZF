package com.zhwld.iscada.em;

public enum FrameEnum implements IEnum {

	YX("遥信", 0),

	YC("遥测", 1),

	MC("脉冲", 2),

	SOE("SOE", 3),

	YK("遥控", 4),

	YT("遥调", 5),
	
	JS("校时", 6),
	
	DDZ("读定值", 7),
	
	XDZ("写定值", 8);

	private String name;
	private int index;

	FrameEnum(String name, int index) {
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

	public static FrameEnum getByIndex(int index) {

		for (FrameEnum frameEnum : FrameEnum.values()) {
			if (frameEnum.getIndex() == index) {
				return frameEnum;
			}
		}
		return null;
	}

	public static int getByName(String name) {
		for (FrameEnum frameEnum : FrameEnum.values()) {

			if (frameEnum.getName().equalsIgnoreCase(name)) {
				return frameEnum.index;
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
		for (FrameEnum frameEnum : FrameEnum.values()) {

			if (frameEnum.getName().equalsIgnoreCase(name)) {
				return frameEnum;
			}
		}
		return null;
	}
}

