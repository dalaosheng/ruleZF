package com.zhwld.iscada.em;

public interface IEnum<E extends Enum<?>, T>{
	int getIndex();

	void setIndex(int index);

	String getName();

	void setName(String name);
	
	String getEnumName();
	
	public  IEnum getEnumByName(String name);
}
