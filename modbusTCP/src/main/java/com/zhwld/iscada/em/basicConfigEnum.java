package com.zhwld.iscada.em;

public class basicConfigEnum {
	
	public enum uartEnum {

		uart1("串口1",1),

		uart2("串口2",2);
		
		String name;
		int index;
		
		uartEnum(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}

	public enum baudrateEnum {
		baudrate1("1200 bps",1200),

		baudrate2("2400 bps",2400),

		baudrate3("4800 bps",4800),
		
		baudrate4("9600 bps",9600),

		baudrate5("14400 bps",14400),
		
		baudrate6("19200 bps",19200);
		
		String name;
		int index;
		
		baudrateEnum(String name, int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
	public enum databitsEnum {
		
		databits1("7位",7),

		databits2("8位",8);
		
		String name;
		int index;
		databitsEnum(String name,int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
	public enum stopbitsEnum {
		stopbits1("1位",1),

		stopbits2("2位",2);
		
		String name;
		int index;
		
		stopbitsEnum(String name,int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
	public enum parityEnum {
		parity1("无校验",0),

		parity2("基校验",1),

		parity3("偶校验",2);
		
		String name;
		int index;
		
		parityEnum(String name,int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
	public enum ruleTypeEnum {
		ruleType1("IEC101规约",0),

		ruleType2("MQTT规约",1);
		
		String name;
		int index;
		
		ruleTypeEnum(String name,int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
	public enum channelRuleEnum {
		channelRule1("MODBUS规约",0),

		channelRule2("DLT645-1997规约",1),
		
		channelRule3("DLT645-2007规约",2);
		
		String name;
		int index;
		
		channelRuleEnum(String name,int index) {
			this.name = name;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
}
