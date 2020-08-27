package com.zhwld.iscada.client.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zhwld.iscada.BaseController;
import com.zhwld.iscada.client.BaseRule;
import com.zhwld.iscada.client.DataForward;
import com.zhwld.iscada.client.DataForwardNew;
import com.zhwld.iscada.client.DotDemo;
import com.zhwld.iscada.client.MqttRule;
import com.zhwld.iscada.client.ParamRule;
import com.zhwld.iscada.client.PropertiesUtil;
import com.zhwld.iscada.em.DataForwardEnum;
import com.zhwld.iscada.em.DotEnum;
import com.zhwld.iscada.em.FrameEnum;
import com.zhwld.iscada.em.basicConfigEnum.baudrateEnum;
import com.zhwld.iscada.em.basicConfigEnum.channelRuleEnum;
import com.zhwld.iscada.em.basicConfigEnum.databitsEnum;
import com.zhwld.iscada.em.basicConfigEnum.parityEnum;
import com.zhwld.iscada.em.basicConfigEnum.ruleTypeEnum;
import com.zhwld.iscada.em.basicConfigEnum.stopbitsEnum;
import com.zhwld.iscada.em.basicConfigEnum.uartEnum;
import com.zhwld.iscada.util.FxUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * modubs TCP
 * 
 * @author Administrator
 *
 */
public class IndexController extends BaseController {

	@FXML
	private TableView<DataForwardNew> tabForward;
	@FXML
	private TableView<ParamRule> tabRule;
	@FXML
	private TableView<DotDemo> tabDot;
	@FXML
	private TableView<DataForward> tabDotForward;
	
	@FXML
	private TableColumn<ParamRule, ComboBox> frameTableColumn;
	@FXML
	private TableColumn<ParamRule, TextField> codeTableColumn;
	@FXML
	private TableColumn<ParamRule, TextField> addrTableColumn;
	@FXML
	private TableColumn<ParamRule, TextField> numTableColumn;
	
	@FXML
	private TableColumn<DotDemo, String> deviceColumn;
	
	@FXML
	private TableColumn<DataForwardNew, String> zfNameColumn;
	@FXML
	private TableColumn<DataForwardNew, String> zfAddrColumn;
	@FXML
	private TableColumn<DataForwardNew, String> zfdotTypeColumn;
	@FXML
	private TableColumn<DataForwardNew, String> zfdotAddrColumn;
	@FXML
	private TableColumn<DataForwardNew, String> zfdotColumn;
	@FXML
	private TableColumn<DataForwardNew, String> zfNumColumn;
	
	@FXML
	private Button add;
	@FXML
	private Button delete;
	@FXML
	private Button exit;
	
	@FXML
	private Button addRow;
	@FXML
	private Button deleteRow;
	@FXML
	private Button exitDot;
	@FXML
	private Button csvImport;
	
	@FXML
	private Button saveConfig;
	@FXML
	private Button clearConfig;
	@FXML
	private Button exitConfig;
	@FXML
	private Button freshConfig;
	
	@FXML
	private Button zfInsertButton;
	@FXML
	private Button zfDeleteButton;
	@FXML
	private Button zfAllButton;
	@FXML
	private Button zfUnselectAllButton;
	
	@FXML
	private Button addRtu;
	@FXML
	private Button deleteRtu;
	@FXML
	private Button ruleParamButton;

	@FXML
	private TextField channelRuleDeviceAddrTextField;
	
	@FXML
	private TreeView<String> dotTreeView;
	@FXML
	private TreeView<String> dataZfTreeView;
	
	@FXML
	private ComboBox<uartEnum> uartComboBox;
	@FXML
	private ComboBox<baudrateEnum> baudrateComboBox;
	@FXML
	private ComboBox<databitsEnum> databitsComboBox;
	@FXML
	private ComboBox<stopbitsEnum> stopbitsComboBox;
	@FXML
	private ComboBox<parityEnum> parityComboBox;
	@FXML
	private ComboBox<ruleTypeEnum> ruleTypeComboBox;
	@FXML
	private ComboBox<channelRuleEnum> channelRuleComboBox;
	@FXML
	private TextField dataTimeIntervalTextField;
	
	
	private Stage stageManual;
	
	private List<ParamRule> list = new ArrayList<ParamRule>();
	
	private List<DotDemo> channelList = new ArrayList<>();
	private List<DotDemo> channelOldList = new ArrayList<>();
	
	private List<DotDemo> dotDemoList = new ArrayList<>();
	
	private DataForwardNew zfDotObject = new DataForwardNew();
	private DataForward dotObject = new DataForward();
	private List<DataForward> zfRTUList = new ArrayList<>();
	private List<DataForward> zfRTUYXList = new ArrayList<>();
	private List<DataForward> zfRTUYCList = new ArrayList<>();
	private List<DataForward> zfRTUMCList = new ArrayList<>();
	private List<DataForwardNew> dotZFRTUList = new ArrayList<>();
	private List<DataForwardNew> dotZFList = new ArrayList<>();
	private List<DataForwardNew> zfYXList = new ArrayList<>();
	private List<DataForwardNew> zfYCList = new ArrayList<>();
	private List<DataForwardNew> zfMCList = new ArrayList<>();
	private List<DataForwardNew> zfYKList = new ArrayList<>();
	private List<DataForwardNew> zfYTList = new ArrayList<>();
	private List<DataForwardNew> zfDZList = new ArrayList<>();
	
	private TreeItem<String> dotTreeItem =new TreeItem<>("通道配置");
	
	private int dotConfigNum = 0;
	private String dotConfigString = "RTU0";

	private static IndexController instance;

	public static IndexController getInstance() {
		return instance;
	}
	
	public List<ParamRule> getList() {
		return list;
	}

	public void setList(List<ParamRule> list) {
		this.list = list;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initBasicConfig();
		
		initRuleTable();
		
		initDotTable();
		
		initDotTreeView();		
		
		initForwardTable();
		
		initDataZfTreeView();
		
		initZFDATATable();
		
	}
	
    private static String readJSONFile(String filePath) {
        File file02 = new File(filePath);
        FileInputStream is = null;
        StringBuilder stringBuilder = null;
        try {
            if (file02.length() != 0) {
                /**
                 * 文件有内容才去读文件
                 */
                is = new FileInputStream(file02);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // stringBuilder.append(line);
                    stringBuilder.append(line);
                }
                reader.close();
                is.close();
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(stringBuilder);

    }
    
    private String uartJSONConfig() {
        
        List<Map<String, Object>> listUart = new ArrayList<Map<String, Object>>();
    	Map<String, Object> mapUart = new HashMap<String, Object>();  
    	mapUart.put("com",uartComboBox.getValue().getIndex()); 
    	mapUart.put("baudrate",baudrateComboBox.getValue().getIndex()); 
    	mapUart.put("databits",databitsComboBox.getValue().getIndex());
    	mapUart.put("stopbits",stopbitsComboBox.getValue().getIndex());
    	mapUart.put("parity",parityComboBox.getValue().getIndex());
    	mapUart.put("deviceAddr",channelRuleDeviceAddrTextField.getText());
    	mapUart.put("ruleType",channelRuleComboBox.getValue().getIndex());
    	mapUart.put("timeInterval",dataTimeIntervalTextField.getText());
		listUart.add(mapUart);
       
        Gson gson = new Gson();
        String endStr ="]}" ;
        String jsonString =gson.toJson(listUart);
        String jsonNewString = "\r" + "\"" + "uartSetting" + "\":[" + jsonString.substring(1,jsonString.length()) + ",";//第二次导入
        return jsonNewString;
    }
    
    private String zfDotJSONConfig() {
        
        List<Map<String, Object>> listzfDot = new ArrayList<Map<String, Object>>();
    	for(DataForwardNew dfn : zfYXList) {
        	Map<String, Object> mapzfDot = new HashMap<String, Object>();  
        	mapzfDot.put("newAddr",dfn.getAddress()); 
        	mapzfDot.put("dotType",0); 
        	mapzfDot.put("oldAddr",dfn.getAddressType());
        	mapzfDot.put("data","0");
        	listzfDot.add(mapzfDot);
    	}
    	for(DataForwardNew dfn : zfYCList) {
        	Map<String, Object> mapzfDot = new HashMap<String, Object>();  
        	mapzfDot.put("newAddr",dfn.getAddress()); 
        	mapzfDot.put("dotType",1); 
        	mapzfDot.put("oldAddr",dfn.getAddressType());
        	mapzfDot.put("data","0");
        	listzfDot.add(mapzfDot);
    	}
    	for(DataForwardNew dfn : zfMCList) {
        	Map<String, Object> mapzfDot = new HashMap<String, Object>();  
        	mapzfDot.put("newAddr",dfn.getAddress()); 
        	mapzfDot.put("dotType",2); 
        	mapzfDot.put("oldAddr",dfn.getAddressType());
        	mapzfDot.put("data","0");
        	listzfDot.add(mapzfDot);
    	}
       
        Gson gson = new Gson();
        String endStr ="]}" ;
        String jsonString =gson.toJson(listzfDot);
        String jsonNewString = "\r" + "\"" + "zfDotSetting" + "\":[" + jsonString.substring(1,jsonString.length()) + "}";//第二次导入
        return jsonNewString;
    }
    
    private String dotJSONSConfig() {
    	List<Map<String, Object>> listYXSave = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> listYCSave = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> listMCSave = new ArrayList<Map<String, Object>>();
    	
    	List<DotDemo> dotNewList = new ArrayList<>();
    	
		int j=0;

		String jsonString = "";
		for(DotDemo dotDemo : channelList) {

			for(int i=0; i<dotDemoList.size(); i++) {		    			
    			if(dotDemoList.get(i).getDevice().equals(dotDemo.getDevice())) {
    				DotDemo d = new DotDemo();
					d.setDevice(dotDemoList.get(i).getDevice());
					d.setNum(dotDemoList.get(i).getNum());
					d.setAddress(dotDemoList.get(i).getAddress());
					d.setIndex(dotDemoList.get(i).getIndex());
    				dotNewList.add(d);
    			}		    			
    		}
			
			for(int i=0; i<dotNewList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();  
				map.put("num",dotNewList.get(i).getNum());
				map.put("addr",dotNewList.get(i).getAddress());
				map.put("data","0");
				if(dotNewList.get(i).getIndex().equals("1")) {
					listYXSave.add(map); 
				}else if(dotNewList.get(i).getIndex().equals("2")) {
					listYCSave.add(map); 
				}else if(dotNewList.get(i).getIndex().equals("3")){
					listMCSave.add(map); 
				}
			}
			
			if(j==0) {
				jsonString =jsonString + "\r" + "\"" + channelList.get(j).getName() + "\":[";
			}else {
				jsonString =jsonString.substring(0,jsonString.length()-1) + "]," + "\r" + "\"" + channelList.get(j).getName() + "\":[";
			}
			
			if (listYXSave != null && listYXSave.size() != 0) {
				jsonString =  jsonString + dotJSONConfig2(listYXSave,"YX") + "},";
		    }else {
		    	jsonString =  jsonString + "{" + "\r" + "\"" + "YX" + "\":[]},";
		    }
			if(listYCSave != null && listYCSave.size() != 0){
		    	jsonString =  jsonString + dotJSONConfig2(listYCSave,"YC") + "},";
		    }else {
		    	jsonString =  jsonString + "{" + "\r" + "\"" + "YC" + "\":[]},";
		    }
			if(listMCSave != null && listMCSave.size() != 0){
		    	jsonString =  jsonString + dotJSONConfig2(listMCSave,"MC") + "},";
		    }else {
		    	jsonString =  jsonString + "{" + "\r" + "\"" + "MC" + "\":[]},";
		    }
			j++;
			dotNewList.clear();
	    	listYXSave.clear();
	    	listYCSave.clear();
	    	listMCSave.clear();
		}
		
		jsonString = jsonString.substring(0,jsonString.length()-1)  + "],";
		
		return jsonString;
    }
    
    private static String dotJSONConfig2(List<Map<String, Object>> list,String dotStr) {
    	String str= "[]";
        if (list == null && list.size() == 0) {
            return str;
        }
        Gson gson = new Gson();
        String jsonString =gson.toJson(list);
        String jsonNewString = 	"{" + "\r" + "\"" + dotStr + "\":["
        						+ jsonString.substring(1,jsonString.length());//第三次导入
        str = jsonNewString;
        return str;
    }
    
    private String paramRuleConfig() {
    	List<Map<String, Object>> listRule = new ArrayList<Map<String, Object>>();
		for(int i=0; i<list.size(); i++) { 
	    	Map<String, Object> map = new HashMap<String, Object>();  
			map.put("frame",list.get(i).getFrame().getValue().getIndex()); 
			map.put("code",list.get(i).getCode().getText().trim()); 
			map.put("address",list.get(i).getAddress().getText().trim());
			map.put("data",list.get(i).getData().getText().trim());

			listRule.add(map); 
		}
		
        Gson gson = new Gson();
        String endStr ="]}" ;
        String jsonString =gson.toJson(listRule);
        String jsonNewString = "\r" + "\"" + "paramRule" + "\":[" + jsonString.substring(1,jsonString.length()) + ",";//第二次导入
        return jsonNewString;// json文件
    }
 
    private static void inputFile(final String jsonString, final String path) {
        // TODO Auto-generated method stub
        new Thread(new Runnable() {
 
            public void run() { 
                // TODO Auto-generated method stub
                WriteConfigJson(jsonString,path);
            }
        }).start();
    }
 
    //输出JSON文件
    public static void WriteConfigJson(String args,String path) {
        //String src = "D:\\AA\\province.json";// 自定义文件路径
 
        File file = new File(path);
 
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(args);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
    
	private void chooseRuleStage(boolean r) {
		if(r==true) {
			
			IEC101Controller iec101Controller = null;
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setIconified(false);
			stage.setResizable(false);
			stage.setMaximized(false);
			try {
				iec101Controller = (IEC101Controller) FxUtils.replaceSceneContent(stage, "fxml/IEC101.fxml",
						IEC101Controller.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iec101Controller.setStage(stage);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					stage.close();
				}
			});
			if (iec101Controller != null) {
				stage.setTitle("IEC101规约配置");
				stage.show();
			}

		}else if(r==false) {
			MQTTController mqttController = null;
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setIconified(false);
			stage.setResizable(false);
			stage.setMaximized(false);
			try {
				mqttController = (MQTTController) FxUtils.replaceSceneContent(stage, "fxml/mqtt.fxml",
						MQTTController.class);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						stage.close();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mqttController.setStage(stage);
			if (mqttController != null) {
				stage.setTitle("mqtt规约配置");
				stage.show();
			}
		}
	}

	private void initBasicConfig() {
		
		boolean[] s= {true};
		uartComboBox.getItems().addAll(uartEnum.values());
		baudrateComboBox.getItems().addAll(baudrateEnum.values());
		databitsComboBox.getItems().addAll(databitsEnum.values());
		stopbitsComboBox.getItems().addAll(stopbitsEnum.values());
		parityComboBox.getItems().addAll(parityEnum.values());
		ruleTypeComboBox.getItems().addAll(ruleTypeEnum.values());
		channelRuleComboBox.getItems().addAll(channelRuleEnum.values());
		ruleTypeComboBox.getSelectionModel().select(0);
		channelRuleComboBox.getSelectionModel().select(0);
		dataTimeIntervalTextField.setText("3000");
		channelRuleDeviceAddrTextField.setText("1");
		uartComboBox.setValue(uartEnum.uart1);
		baudrateComboBox.setValue(baudrateEnum.baudrate4);
		databitsComboBox.setValue(databitsEnum.databits2);
		stopbitsComboBox.setValue(stopbitsEnum.stopbits1);
		parityComboBox.setValue(parityEnum.parity3);
		uartComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				switch(newValue.toString()){
				    case "串口0" :
				    	uartComboBox.setValue(uartEnum.uart1);
				    	break;
				    case "串口1" :
				    	uartComboBox.setValue(uartEnum.uart2);
				    	break;
				    default :
				}
				
			}
        });
		
		baudrateComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				switch(newValue.toString()){
				    case "1200 bps" :
				    	baudrateComboBox.setValue(baudrateEnum.baudrate1);
				    	break;
				    case "2400 bps" :
				    	baudrateComboBox.setValue(baudrateEnum.baudrate2);
				    	break;
				    case "4800 bps" :
				    	baudrateComboBox.setValue(baudrateEnum.baudrate3);
				    	break;
				    case "9600 bps" :
				    	baudrateComboBox.setValue(baudrateEnum.baudrate4);
				    	break;
				    case "14400 bps" :
				    	baudrateComboBox.setValue(baudrateEnum.baudrate5);
				    	break;
				    case "19200 bps" :
				    	baudrateComboBox.setValue(baudrateEnum.baudrate6);
				    	break;
				    default :
				}
				
			}
        });
		
		databitsComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				switch(newValue.toString()){
				    case "8位" :
				    	databitsComboBox.setValue(databitsEnum.databits1);
				    	break;
				    case "7位" :
				    	databitsComboBox.setValue(databitsEnum.databits2);
				    	break;
				    default :
				}
				
			}
        });
		
		stopbitsComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				switch(newValue.toString()){
				    case "1位" :
				    	stopbitsComboBox.setValue(stopbitsEnum.stopbits1);
				    	break;
				    case "2位" :
				    	stopbitsComboBox.setValue(stopbitsEnum.stopbits2);
				    	break;
				    default :
				}
				
			}
        });
		
		parityComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				switch(newValue.toString()){
				    case "无校验" :
				    	parityComboBox.setValue(parityEnum.parity1);
				    	break;
				    case "基校验" :
				    	parityComboBox.setValue(parityEnum.parity2);
				    	break;
				    case "偶校验" :
				    	parityComboBox.setValue(parityEnum.parity3);
				    	break;
				    default :
				}
				
			}
        });
		
		channelRuleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				if(newValue.toString().equals(channelRuleEnum.channelRule1.toString())) {
					codeTableColumn.setText("功能码(H)");
					addrTableColumn.setText("起始地址(H)");
					numTableColumn.setText("请求数量(H)");
					channelRuleDeviceAddrTextField.setText("1");
					channelRuleComboBox.setValue(channelRuleEnum.channelRule1);
					baudrateComboBox.setValue(baudrateEnum.baudrate4);
				}else if(newValue.toString().equals(channelRuleEnum.channelRule2.toString())) {
					codeTableColumn.setText("控制码(H)");
					addrTableColumn.setText("数据标识(H)");
					numTableColumn.setText("数据长度(H)");
					channelRuleDeviceAddrTextField.setText("150");
					channelRuleComboBox.setValue(channelRuleEnum.channelRule2);
					baudrateComboBox.setValue(baudrateEnum.baudrate1);
				}else if(newValue.toString().equals(channelRuleEnum.channelRule3.toString())) {
					codeTableColumn.setText("控制码(H)");
					addrTableColumn.setText("数据标识(H)");
					numTableColumn.setText("数据长度(H)");
					channelRuleDeviceAddrTextField.setText("150");
					channelRuleComboBox.setValue(channelRuleEnum.channelRule3);
					baudrateComboBox.setValue(baudrateEnum.baudrate1);
				}
			}
        });
		
		ruleTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				if(newValue.toString().equals(ruleTypeEnum.ruleType1.toString())) {
					s[0]=true;
				}else {
					s[0]=false;
				}
			}
        });
		ruleParamButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {	
		    	chooseRuleStage(s[0]);
		    }
		});			
		
	}
    
    private void initForwardTable() {
    	    
    	zfNumColumn.setCellValueFactory(new PropertyValueFactory<DataForwardNew, String>("num"));
    	
    	zfNameColumn.setCellValueFactory(new PropertyValueFactory<DataForwardNew, String>("name"));
		
		zfAddrColumn.setCellValueFactory(new PropertyValueFactory<DataForwardNew, String>("address"));
		
		zfdotTypeColumn.setCellValueFactory(new PropertyValueFactory<DataForwardNew, String>("type"));

		zfdotAddrColumn.setCellValueFactory(new PropertyValueFactory<DataForwardNew, String>("addressType"));

		zfdotColumn.setCellValueFactory(new PropertyValueFactory<DataForwardNew, String>("dotType"));
		
		zfNumColumn.setVisible(true);
		zfdotTypeColumn.setVisible(false);
		zfdotAddrColumn.setVisible(false);
		zfdotColumn.setVisible(false);
		
    }
    
    private String channelDeviceValue(String str) {
    	int i =0;
		for (DotDemo dotDemo : channelList) {
			if(str.equals(channelList.get(i).getName())) {
				return channelList.get(i).getDevice();
			}
			i++;
		}
		return null;
    }
    
    private void initDotTreeView() {
		ObservableList<DotEnum> listDot = FXCollections.observableArrayList(DotEnum.values());

		if (channelList != null && channelList.size() > 0) {
			int i =0;
			for (DotDemo dotDemo : channelList) {
				TreeItem<String> tItem =new TreeItem<>(channelList.get(i).getName());
				if (listDot != null && listDot.size() > 0) {
					int j =0;
					for (DotEnum dotEnum : listDot) {
						TreeItem<String> tItem2 =new TreeItem<>(listDot.get(j).getEnumName());
						tItem.getChildren().add(tItem2);
						j++;
					}
				}
				dotTreeItem.getChildren().add(tItem);
				i++;
			}
		}
		
		dotTreeItem.setExpanded(true);
		dotTreeView.setRoot(dotTreeItem);  
//		dotTreeView.setShowRoot(false);//隐藏根目录
		dotTreeView.getSelectionModel().select(0);
		dotTreeView.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
			if (newValue != null && newValue.getValue() != null) {
				
				if(newValue.getValue().equals("通道配置")) {
					deviceColumn.setVisible(true);
					tabDot.getItems().clear();
					tabDot.getItems().addAll(channelList);
				}else if(newValue.getValue().equals("遥信配置")){
					deviceColumn.setVisible(false);
					dotConfig(1,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("遥测配置")){
					deviceColumn.setVisible(false);
					dotConfig(2,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("脉冲配置")){
					deviceColumn.setVisible(false);
					dotConfig(3,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("遥控配置")){
					deviceColumn.setVisible(false);
					dotConfig(4,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("遥调配置")){
					deviceColumn.setVisible(false);
					dotConfig(5,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("定值配置")){
					deviceColumn.setVisible(false);
					dotConfig(6,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("统计点配置")){
					deviceColumn.setVisible(false);
					dotConfig(7,channelDeviceValue(newValue.getParent().getValue()));					
				}else if(newValue.getValue().equals("逻辑计算配置")){
					deviceColumn.setVisible(false);
					tabDot.getItems().clear();
				}else if(newValue.getValue().equals("模拟计算配置")){
					deviceColumn.setVisible(false);
					tabDot.getItems().clear();
				}else{
					if (channelList != null && channelList.size() > 0) {
						tabDot.getItems().clear();
						int i =0;
						for (DotDemo dotDemo : channelList) {
							if(newValue.getValue().equals(channelList.get(i).getName())) {
								deviceColumn.setVisible(true);
								tabDot.getItems().clear();
								tabDot.getItems().addAll(channelList.get(i));
							}
							i++;
						}
					}
				}
			}
		});
    }
    
    private void initDataZfTreeView() { 
		ObservableList<DataForwardEnum> listDot = FXCollections.observableArrayList(DataForwardEnum.values());
		TreeItem<String> treeStr =new TreeItem<>();
		AtomicLong id = new AtomicLong();
		id.incrementAndGet();
		TreeItem<String> treeStr1 =new TreeItem<>("转发RTU00"+id);
		treeStr.getChildren().add(treeStr1);
		if (listDot != null && listDot.size() > 0) {
			int i =0;
			for (DataForwardEnum DataForwardEnum : listDot) {
				TreeItem<String> tItem =new TreeItem<>(listDot.get(i).getEnumName());
				treeStr1.getChildren().add(tItem);
				i++;
			}
		}
		treeStr.setExpanded(true);
		treeStr1.setExpanded(true);
		dataZfTreeView.setRoot(treeStr);  
		dataZfTreeView.setShowRoot(false);
//		dataZfTreeView.getSelectionModel().select(0);
		
		addRtu.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	id.incrementAndGet();
		    	TreeItem<String> treeStrAdd =new TreeItem<>("转发RTU00"+id);
		    	if (listDot != null && listDot.size() > 0) {
					int i =0;
					for (DataForwardEnum DataForwardEnum : listDot) {
						TreeItem<String> tItem =new TreeItem<>(listDot.get(i).getEnumName());
						treeStrAdd.getChildren().add(tItem);
						i++;
					}
				}
		    	treeStr.getChildren().add(treeStrAdd);
		    }
		});
		
		deleteRtu.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	TreeItem<String> treeStrDelete = dataZfTreeView.getSelectionModel().getSelectedItem();
    			if(treeStrDelete == null)
				{
					FxUtils.alertWarning("请选择树节点");
							
					return;
				}
		    	id.decrementAndGet();
		    	treeStr.getChildren().remove(treeStrDelete);
		    }
		});
		
		dataZfTreeView.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {	
			String value = newValue.getValue();
			if (newValue != null && value != null) {
				if(value.equals("转发遥信配置")) {
					dataConfig(1,"遥信",zfYXList);					
					dotConfigNum = 0;
					dotConfigString="转发RTU00"+id;
					zfNumColumn.setVisible(false);
					zfdotTypeColumn.setVisible(true);
					zfdotAddrColumn.setVisible(true);
					zfdotColumn.setVisible(true);
				}else if(value.equals("转发遥测配置")) {
					dataConfig(2,"遥测",zfYCList);
					dotConfigNum = 1;
					dotConfigString="转发RTU00"+id;
					zfNumColumn.setVisible(false);
					zfdotTypeColumn.setVisible(true);
					zfdotAddrColumn.setVisible(true);
					zfdotColumn.setVisible(true);
				}else if(value.equals("转发脉冲配置")) {
					dataConfig(3,"脉冲",zfMCList);
					dotConfigNum = 2;
					dotConfigString="转发RTU00"+id;
					zfNumColumn.setVisible(false);
					zfdotTypeColumn.setVisible(true);
					zfdotAddrColumn.setVisible(true);
					zfdotColumn.setVisible(true);
				}else if(value.equals("转发遥控配置")) {
					dataConfig(5,"遥控",zfYKList);
					dotConfigNum = 3;
					dotConfigString="转发RTU00"+id;
					zfNumColumn.setVisible(false);
					zfdotTypeColumn.setVisible(true);
					zfdotAddrColumn.setVisible(true);
					zfdotColumn.setVisible(true);
				}else if(value.equals("转发遥调配置")) {
					dotConfigNum = 4;
					dotConfigString="转发RTU00"+id;
					zfNumColumn.setVisible(false);
					zfdotTypeColumn.setVisible(true);
					zfdotAddrColumn.setVisible(true);
					zfdotColumn.setVisible(true);
				}else if(value.equals("转发定值配置")) {
					dotConfigNum = 5;
					dotConfigString="转发RTU00"+id;
					dataConfig(7,"定值",zfDZList);
					zfNumColumn.setVisible(false);
					zfdotTypeColumn.setVisible(true);
					zfdotAddrColumn.setVisible(true);
					zfdotColumn.setVisible(true);
				}else {
					tabDotForward.getItems().clear();
					tabForward.getItems().clear();
					zfNumColumn.setVisible(true);
					zfdotTypeColumn.setVisible(false);
					zfdotAddrColumn.setVisible(false);
					zfdotColumn.setVisible(false);
					tabDotForward.getItems().addAll(zfRTUList);
					tabForward.getItems().addAll(dotZFRTUList);
				}
			}
		});
    }
    
    private List<String> zfDotSelect() {
    	List<String> dotStringList = new ArrayList<>();
    	for(DataForward df : zfRTUList) {
    		for(DataForwardNew dfn : dotZFRTUList) {
        		if(dfn.getName().equals("转发"+df.getName())) {
        			dotStringList.add(df.getDevice());
        		}
    		}
		}
    	return dotStringList;
    }
    
    private void initZFDATATable() {
    	TableColumn<DataForward, CheckBox> nameColumn = new TableColumn<DataForward, CheckBox>("名称");
    	nameColumn.setCellValueFactory(new PropertyValueFactory<DataForward, CheckBox>("name"));
		
		TableColumn<DataForward, String> codeColumn = new TableColumn<DataForward, String>("序号");
		codeColumn.setCellValueFactory(new PropertyValueFactory<DataForward, String>("num"));
		
		TableColumn<DataForward, String> addressColumn = new TableColumn<DataForward, String>("地址");
		addressColumn.setCellValueFactory(new PropertyValueFactory<DataForward, String>("address"));	
		
		dataZfTreeView.getSelectionModel().select(0);
    	tabDotForward.getItems().clear();
		tabForward.getItems().clear();
		zfNumColumn.setVisible(true);
		zfdotTypeColumn.setVisible(false);
		zfdotAddrColumn.setVisible(false);
		zfdotColumn.setVisible(false);
		int i=0;						
		zfRTUList.clear();
		dotZFRTUList.clear();
		for(DotDemo d : channelList) {
			DataForward df = new DataForward();
			df.setAddress(d.getAddress());
			df.setDevice(d.getDevice());
			df.setName(d.getName());
			df.setNum(d.getNum());
			zfRTUList.add(df);
			DataForwardNew dn = new DataForwardNew();
			dn.setAddress(d.getAddress());
			dn.setName("转发"+d.getName());
			dn.setNum(d.getNum());
			i++;
		}
		
		tabDotForward.getItems().addAll(zfRTUList);
		tabForward.getItems().addAll(dotZFRTUList);
		
		zfInsertButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	if(tabDotForward.getSelectionModel().getSelectedItem()==null) {
					FxUtils.alertError("请选中要转发RTU");					
					return;
				}
		    	for(DataForwardNew dzfl:dotZFRTUList) {
			    	if(dzfl.getName().equals("转发"+tabDotForward.getSelectionModel().getSelectedItem().getName())) {
						FxUtils.alertError(dzfl.getName()+"已存在");					
						return;
					}
		    	}
		    	for(DataForwardNew dzfl:zfYXList) {
			    	if(dzfl.getType().equals(tabDotForward.getSelectionModel().getSelectedItem().getName())) {
						FxUtils.alertError(dzfl.getType()+"已存在");					
						return;
					}
		    	}
		    	for(DataForwardNew dzfl:zfYCList) {
			    	if(dzfl.getType().equals(tabDotForward.getSelectionModel().getSelectedItem().getName())) {
						FxUtils.alertError(dzfl.getType()+"已存在");					
						return;
					}
		    	}
		    	for(DataForwardNew dzfl:zfMCList) {
			    	if(dzfl.getType().equals(tabDotForward.getSelectionModel().getSelectedItem().getName())) {
						FxUtils.alertError(dzfl.getType()+"已存在");					
						return;
					}
		    	}
		    	tabForward.getItems().clear();
		    	if(zfdotTypeColumn.isVisible()==true) {
		    		DataForwardNew dfn = new DataForwardNew();
	    	    	dfn.setNum(String.valueOf(tabDotForward.getSelectionModel().getSelectedItem().getNum()));
	    	    	dfn.setAddressType(tabDotForward.getSelectionModel().getSelectedItem().getAddress());
	    	    	dfn.setType(tabDotForward.getSelectionModel().getSelectedItem().getName());
		    		if(tabDotForward.getSelectionModel().getSelectedItem().getIndex().equals("1")) {
		    			int calc=1;
		    			if(zfYXList!=null && zfYXList.size()!=0) {
		    				for(DataForwardNew d : zfYXList) {
		    					calc=Integer.valueOf(d.getAddress())+1;
		    				}
		    			}else {
		    				calc=1;
		    			}
				    	dfn.setName("转发遥信"+calc);
		    	    	dfn.setAddress(""+calc);
		    	    	dfn.setDotType("遥信");
		    	    	zfYXList.add(dfn);
		    	    	tabForward.getItems().addAll(zfYXList);
		    		}else if(tabDotForward.getSelectionModel().getSelectedItem().getIndex().equals("2")) {
		    			int calc=1;
		    			if(zfYCList!=null && zfYCList.size()!=0) {
		    				for(DataForwardNew d : zfYCList) {
		    					calc=Integer.valueOf(d.getAddress())+1;
		    				}
		    			}else {
		    				calc=1;
		    			}
				    	dfn.setName("转发遥测"+calc);
		    	    	dfn.setAddress(""+calc);
				    	dfn.setDotType("遥测");
				    	zfYCList.add(dfn);
		    	    	tabForward.getItems().addAll(zfYCList);
		    		}else if(tabDotForward.getSelectionModel().getSelectedItem().getIndex().equals("3")) {
		    			int calc=1;
		    			if(zfMCList!=null && zfMCList.size()!=0) {
		    				for(DataForwardNew d : zfMCList) {
		    					calc=Integer.valueOf(d.getAddress())+1;
		    				}
		    			}else {
		    				calc=1;
		    			}
				    	dfn.setName("转发脉冲"+calc);
		    	    	dfn.setAddress(""+calc);
				    	dfn.setDotType("脉冲");
				    	zfMCList.add(dfn);
		    	    	tabForward.getItems().addAll(zfMCList);
		    		}
		    	}else {
		    		DataForwardNew dfn = new DataForwardNew();
			    	dfn.setName("转发"+tabDotForward.getSelectionModel().getSelectedItem().getName());
	    	    	dfn.setNum(String.valueOf(tabDotForward.getSelectionModel().getSelectedItem().getNum()));
	    	    	dfn.setAddress(tabDotForward.getSelectionModel().getSelectedItem().getAddress());
	    	    	dotZFRTUList.add(dfn);
	    	    	tabForward.getItems().addAll(dotZFRTUList);
		    	}	    	

		    }
		});
		
		zfDeleteButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	if(tabForward.getSelectionModel().getSelectedItem()==null) {
					FxUtils.alertError("请选中要删除的转发RTU");					
					return;
				}
		    	if(zfdotTypeColumn.isVisible()==true) {
			    	if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发遥信配置")) {
				    	zfYXList.clear();
				    	tabForward.getItems().remove(tabForward.getSelectionModel().getSelectedItem());
				    	zfYXList.addAll(tabForward.getItems());
		    		}else if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发遥测配置")) {
		    			zfYCList.clear();
		    			tabForward.getItems().remove(tabForward.getSelectionModel().getSelectedItem());
		    			zfYCList.addAll(tabForward.getItems());
		    		}else if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发脉冲配置")) {
		    			zfMCList.clear();
		    			tabForward.getItems().remove(tabForward.getSelectionModel().getSelectedItem());
		    			zfMCList.addAll(tabForward.getItems());
		    		}
		    	}else {
		    		dotZFRTUList.clear();
		    		tabForward.getItems().remove(tabForward.getSelectionModel().getSelectedItem());
		    		dotZFRTUList.addAll(tabForward.getItems());
		    	}
		    }
		});
		
		zfUnselectAllButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	if(zfdotTypeColumn.isVisible()==true) {
			    	if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发遥信配置")) {
			    		zfYXList.clear();
		    	    	tabForward.getItems().clear();
		    		}else if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发遥测配置")) {
		    			zfYCList.clear();
		    	    	tabForward.getItems().clear();
		    		}else if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发脉冲配置")) {
		    			zfMCList.clear();
		    	    	tabForward.getItems().clear();
		    		}
		    	}else {
		    		dotZFRTUList.clear();
		    		tabForward.getItems().clear();
		    	}
		    }
		});
		
		zfAllButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	if(zfdotTypeColumn.isVisible()==true) {
			    	tabForward.getItems().clear();
			    	if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发遥信配置")) {
			    		zfYXList.clear();
			    		for(DataForward df : zfRTUYXList) {
			    			DataForwardNew dfn = new DataForwardNew();
			    	    	dfn.setNum(df.getNum());
			    	    	dfn.setAddressType(df.getAddress());
			    	    	dfn.setType(df.getName());
			    	    	int calc=1;
			    			if(zfYXList!=null && zfYXList.size()!=0) {
			    				for(DataForwardNew d : zfYXList) {
			    					calc=Integer.valueOf(d.getAddress())+1;
			    				}
			    			}else {
			    				calc=1;
			    			}
					    	dfn.setName("转发遥信"+calc);
			    	    	dfn.setAddress(""+calc);
			    	    	dfn.setDotType("遥信");
			    	    	zfYXList.add(dfn);
			    		}
		    	    	tabForward.getItems().addAll(zfYXList);
		    		}else if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发遥测配置")) {
		    			zfYCList.clear();
			    		for(DataForward df : zfRTUYCList) {
			    			DataForwardNew dfn = new DataForwardNew();
			    	    	dfn.setNum(df.getNum());
			    	    	dfn.setAddressType(df.getAddress());
			    	    	dfn.setType(df.getName());
			    	    	int calc=1;
			    			if(zfYCList!=null && zfYCList.size()!=0) {
			    				for(DataForwardNew d : zfYCList) {
			    					calc=Integer.valueOf(d.getAddress())+1;
			    				}
			    			}else {
			    				calc=1;
			    			}
					    	dfn.setName("转发遥测"+calc);
			    	    	dfn.setAddress(""+calc);
			    	    	dfn.setDotType("遥测");
			    	    	zfYCList.add(dfn);
			    		}
		    	    	tabForward.getItems().addAll(zfYCList);
		    		}else if(dataZfTreeView.getSelectionModel().getSelectedItem().getValue().equals("转发脉冲配置")) {
		    			zfMCList.clear();
			    		for(DataForward df : zfRTUMCList) {
			    			DataForwardNew dfn = new DataForwardNew();
			    	    	dfn.setNum(df.getNum());
			    	    	dfn.setAddressType(df.getAddress());
			    	    	dfn.setType(df.getName());
			    	    	int calc=1;
			    			if(zfMCList!=null && zfMCList.size()!=0) {
			    				for(DataForwardNew d : zfMCList) {
			    					calc=Integer.valueOf(d.getAddress())+1;
			    				}
			    			}else {
			    				calc=1;
			    			}
					    	dfn.setName("转发脉冲"+calc);
			    	    	dfn.setAddress(""+calc);
			    	    	dfn.setDotType("脉冲");
			    	    	zfMCList.add(dfn);
			    		}
		    	    	tabForward.getItems().addAll(zfMCList);
		    		}
		    	}else {
		    		tabForward.getItems().clear();
		    		dotZFRTUList.clear();
		    		for(DataForward df : zfRTUList) {
		    			DataForwardNew dfn = new DataForwardNew();
				    	dfn.setName("转发"+df.getName());
		    	    	dfn.setNum(String.valueOf(df.getNum()));
		    	    	dfn.setAddress(df.getAddress());
		    	    	dotZFRTUList.add(dfn);
		    		}
	    	    	tabForward.getItems().addAll(dotZFRTUList);
		    	}
		    }
		});
		
		clearConfig.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	String path = PropertiesUtil.getProjectPath(this.getClass())+("/save/config.json");
		    	java.util.Optional<ButtonType> clearJson = FxUtils.showAndWaitUtilityAlert(AlertType.CONFIRMATION,"清空","确定清空配置文件吗");
				if ((clearJson.isPresent() == false) || (clearJson.get() != ButtonType.OK))
				{
					return;
				}
		    	WriteConfigJson("[]",path);

				FxUtils.alertInfo("已成功清空");
		    	System.out.println(readJSONFile(path));
		    }
		});
		
		saveConfig.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	for(DotDemo d : channelList) {
			    	if(d.getDevice()==null) {
						FxUtils.alertError(d.getName()+"无装置信息");					
						return;
					}
		    	}
		    	java.util.Optional<ButtonType> saveJson = FxUtils.showAndWaitUtilityAlert(AlertType.CONFIRMATION,"保存","保存将会替换原有信息，确定保存吗？");
				if ((saveJson.isPresent() == false) || (saveJson.get() != ButtonType.OK))
				{
					return;
				}
		    	String path = PropertiesUtil.getProjectPath(this.getClass())+("/json/config1.json");
		        String path2 = PropertiesUtil.getProjectPath(this.getClass())+("/save/config.json");
		        String zfRuleString = readJSONFile(path);
		        if(zfRuleString.equals("null")) {
		        	zfRuleString="{\""+"rule"+"\":[],";
		        	System.out.println(zfRuleString);
		        }
		        String jsonString =zfRuleString + uartJSONConfig() + paramRuleConfig() + dotJSONSConfig()+zfDotJSONConfig();   
		        inputFile(jsonString,path2);// json文件
		        System.out.println(jsonString);
		        if(jsonString != null)
				{
					FxUtils.alertInfo("保存成功");
					
					return;
				}
		    }
		});	
		
		freshConfig.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	dataZfTreeView.getSelectionModel().select(0);
		    	tabDotForward.getItems().clear();
				tabForward.getItems().clear();
				zfNumColumn.setVisible(true);
				zfdotTypeColumn.setVisible(false);
				zfdotAddrColumn.setVisible(false);
				zfdotColumn.setVisible(false);
				int i=0;						
				zfRTUList.clear();
				dotZFRTUList.clear();
				for(DotDemo d : channelList) {
					DataForward df = new DataForward();
					df.setAddress(d.getAddress());
					df.setDevice(d.getDevice());
					df.setName(d.getName());
					df.setNum(d.getNum());
					zfRTUList.add(df);
					DataForwardNew dn = new DataForwardNew();
					dn.setAddress(d.getAddress());
					dn.setName("转发"+d.getName());
					dn.setNum(d.getNum());
					i++;
				}
				
				tabDotForward.getItems().addAll(zfRTUList);
				tabForward.getItems().addAll(dotZFRTUList);
		    }
		});
		
		exitConfig.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
			try
			{
		    	Node node = (Node)event.getSource();
		    	node.getScene().getWindow().hide();
			}catch(Exception ex)
			{
				
			}
		    }
		});	

		tabDotForward.getColumns().addAll(nameColumn,codeColumn,addressColumn);
			
    }
    
    private void initRuleTable() {
		frameTableColumn.setCellValueFactory(new PropertyValueFactory<ParamRule, ComboBox>("frame"));
		codeTableColumn.setCellValueFactory(new PropertyValueFactory<ParamRule, TextField>("code"));
		addrTableColumn.setCellValueFactory(new PropertyValueFactory<ParamRule, TextField>("address"));
		numTableColumn.setCellValueFactory(new PropertyValueFactory<ParamRule, TextField>("data"));
		
		add.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {	
				List<ParamRule> addList = ParamRule.getAddList();
				list.addAll(addList);
				ObservableList observableListAnalog = FXCollections.observableArrayList(addList);
				tabRule.getItems().addAll(observableListAnalog);
		    }
		});	
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {	
		    	ParamRule selectedItem = tabRule.getSelectionModel().getSelectedItem();
		    	if(selectedItem==null) {
					FxUtils.alertError("请选中要删除的行");					
					return;
				}
		    	tabRule.getItems().remove(selectedItem);
		    	list.remove(selectedItem);
		    }
		});	
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
			try
			{
		    	Node node = (Node)event.getSource();
		    	node.getScene().getWindow().hide();
			}catch(Exception ex)
			{
				
			}
		    }
		});

	}
    
    private void initDotTable() {

		TableColumn<DotDemo, TextField> numColumn = new TableColumn<DotDemo, TextField>("编号");
		numColumn.setCellValueFactory(new PropertyValueFactory<DotDemo, TextField>("num"));
		
		TableColumn<DotDemo, TextField> nameColumn = new TableColumn<DotDemo, TextField>("名称");
		nameColumn.setCellValueFactory(new PropertyValueFactory<DotDemo, TextField>("name"));
		
		TableColumn<DotDemo, TextField> addressColumn = new TableColumn<DotDemo, TextField>("地址");
		addressColumn.setCellValueFactory(new PropertyValueFactory<DotDemo, TextField>("address"));
		
		deviceColumn.setCellValueFactory(new PropertyValueFactory<DotDemo, String>("device"));
		tabDot.getColumns().add(numColumn);
		tabDot.getColumns().add(nameColumn);
		tabDot.getColumns().add(addressColumn);

		AtomicLong id = new AtomicLong();
		id.incrementAndGet();
		
		DotDemo dotDemo = new DotDemo();
		dotDemo.setNum(""+id);
		dotDemo.setAddress(""+id);
    	dotDemo.setName("RTU00"+id);
    	channelList.add(dotDemo);
    	tabDot.getItems().addAll(channelList);
    	
		addRow.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	TreeItem<String> selectedItem = dotTreeView.getSelectionModel().getSelectedItem();
		    	if(!selectedItem.getValue().equals("通道配置")) {
					FxUtils.alertError("请选中通道配置");					
					return;
				}
		    	tabDot.getItems().clear();
		    	dotTreeItem.getChildren().clear();
		    	id.incrementAndGet();
				DotDemo dotDemo = new DotDemo();
				dotDemo.setNum(""+id);
				dotDemo.setAddress(""+id);
		    	dotDemo.setName("RTU00"+id);
		    	channelList.add(dotDemo);
		    	tabDot.getItems().addAll(channelList);
		    	ObservableList<DotEnum> listDot = FXCollections.observableArrayList(DotEnum.values());
		    	if (channelList != null && channelList.size() > 0) {
					int i =0;
					for (DotDemo dotDemo2 : channelList) {
						TreeItem<String> tItem =new TreeItem<>(channelList.get(i).getName());
						if (listDot != null && listDot.size() > 0) {
							int j =0;
							for (DotEnum dotEnum : listDot) {
								TreeItem<String> tItem2 =new TreeItem<>(listDot.get(j).getEnumName());
								tItem.getChildren().add(tItem2);
								j++;
							}
						}
						dotTreeItem.getChildren().add(tItem);
						i++;
					}
				}
		    	tabDotForward.getItems().clear();
				tabForward.getItems().clear();
				int i1=0;
				if(!channelOldList.toString().equals(channelList.toString())) {						
					zfRTUList.clear();
					dotZFRTUList.clear();
					for(DotDemo d : channelList) {
						DataForward df = new DataForward();
						df.setAddress(d.getAddress());
						df.setDevice(d.getDevice());
						df.setName(d.getName());
						df.setNum(d.getNum());
						zfRTUList.add(df);
						DataForwardNew dn = new DataForwardNew();
						dn.setAddress(d.getAddress());
						dn.setName("转发"+d.getName());
						dn.setNum(d.getNum());
						i1++;
					}
					channelOldList.clear();
					channelOldList.addAll(channelList);
				}
				
				tabDotForward.getItems().addAll(zfRTUList);
				tabForward.getItems().addAll(dotZFRTUList);
		    }
		});	
		
		deleteRow.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {	
		    	DotDemo selectedItem = tabDot.getSelectionModel().getSelectedItem();
		    	if(selectedItem==null) {
					FxUtils.alertError("请选中要删除的行");					
					return;
				}
		    	dotTreeItem.getChildren().clear();
		    	channelList.remove(selectedItem);
		    	tabDot.getItems().remove(selectedItem);
		    	ObservableList<DotEnum> listDot = FXCollections.observableArrayList(DotEnum.values());
		    	if (channelList != null && channelList.size() > 0) {
					int i =0;
					for (DotDemo dotDemo2 : channelList) {
						TreeItem<String> tItem =new TreeItem<>(channelList.get(i).getName());
						if (listDot != null && listDot.size() > 0) {
							int j =0;
							for (DotEnum dotEnum : listDot) {
								TreeItem<String> tItem2 =new TreeItem<>(listDot.get(j).getEnumName());
								tItem.getChildren().add(tItem2);
								j++;
							}
						}
						dotTreeItem.getChildren().add(tItem);
						i++;
					}
				}
		    }
		});	
		
		csvImport.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	TreeItem<String> selectedItem = dotTreeView.getSelectionModel().getSelectedItem();
		    	if(!selectedItem.getValue().substring(0,selectedItem.getValue().length()-3).equals("RTU")) {
					FxUtils.alertError("请选中RTU");					
					return;
				}
		    	FileChooser fileChooser = new FileChooser();
//		    	String filePath = IndexController.class.getClassLoader().getResource("csv/HJJC_Modbus_V4.00.csv").getPath();
		    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

		        try {
		            // 创建CSV读对象
		            CsvReader csvReader = null;
		            File file = null;
		            file = fileChooser.showOpenDialog(stageManual);
					String filePath = file.getPath();
		            DataInputStream in = new DataInputStream(new FileInputStream(file));
		            csvReader = new CsvReader(new InputStreamReader(in,"gbk"));//解决中文乱码

		            // 跳过读表头
		            csvReader.readHeaders();
		            ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据 
		            while (csvReader.readRecord()){
//		                // 读一整行
//		                System.out.println(csvReader.getRawRecord());
		                // 读这行的某一列
//		                System.out.println(csvReader.get("Link"));
		                csvList.add(csvReader.getValues());
		            }
		            csvReader.close();
		            
			    	for(int row=0;row<csvList.size();row++){ 
			            DotDemo dotDemo = new DotDemo();
			            dotDemo.setDevice(file.getName());
			    		dotDemo.setNum(String.valueOf(row));
			    		dotDemo.setIndex(csvList.get(row)[0]); 
			    		dotDemo.setAddress(csvList.get(row)[1]);//取得第row行第2列的数据
			            if(csvList.get(row)[0].equals("1")) {
				    		dotDemo.setName(csvList.get(row)[3]);
			            }else {
			            	dotDemo.setName(csvList.get(row)[7]);
			            }
				    	dotDemoList.add(dotDemo);
//			    		System.out.println(cell+"    "+ csvList.get(row)[2]+"    "+ csvList.get(row)[3]+"   ");
			    	}
			    	if(dotDemoList != null)
					{
						FxUtils.alertInfo("导入成功");
						tabDot.getItems().clear();
						int i=0;
						for (DotDemo dotDemo : channelList) {
							if(selectedItem.getValue().equals(channelList.get(i).getName())) {
								channelList.get(i).setDevice(file.getName());
						    	tabDot.getItems().add(channelList.get(i));
							}
							i++;
						}
						tabDotForward.getItems().clear();
						tabForward.getItems().clear();
						int i1=0;
						if(!channelOldList.toString().equals(channelList.toString())) {						
							zfRTUList.clear();
							dotZFRTUList.clear();
							for(DotDemo d : channelList) {
								DataForward df = new DataForward();
								df.setAddress(d.getAddress());
								df.setDevice(d.getDevice());
								df.setName(d.getName());
								df.setNum(d.getNum());
								zfRTUList.add(df);
								DataForwardNew dn = new DataForwardNew();
								dn.setAddress(d.getAddress());
								dn.setName("转发"+d.getName());
								dn.setNum(d.getNum());
								i1++;
							}
							channelOldList.clear();
							channelOldList.addAll(channelList);
						}
						
						tabDotForward.getItems().addAll(zfRTUList);
						tabForward.getItems().addAll(dotZFRTUList);
						
						return;
					}else {
						FxUtils.alertError("导入失败");
						
						return;
					}
		        } catch (IOException e) {
//		            e.printStackTrace();
		        }
		    	
		    }
		});	
		
		exitDot.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
			try
			{
		    	Node node = (Node)event.getSource();
		    	node.getScene().getWindow().hide();
			}catch(Exception ex)
			{
				
			}
		    }
		});
		
	}
    
    private void dataConfig(int i,String dot,List<DataForwardNew> listData) {
    	tabDotForward.getItems().clear();
    	tabForward.getItems().clear();
    	tabForward.getItems().addAll(listData);
    	List<DataForward> dataListNew = new ArrayList<>();
    	int num=0;
    	for(String s : zfDotSelect()) {
    		for(DotDemo d : writeDotConfig(i,s)) {
        		if(String.valueOf(i).equals(d.getIndex())) {
        	    	DataForward df = new DataForward();
        	    	df.setNum(String.valueOf(num));
        	    	df.setAddress(d.getAddress());
        			df.setName(d.getName());
        			df.setIndex(String.valueOf(i));
        			if(i==1) {
        				zfRTUYXList.add(df);
        			}else if(i==2) {
        				zfRTUYCList.add(df);
        			}else if(i==3) {
        				zfRTUMCList.add(df);
        			}
        			dataListNew.add(df);
        			num++;
        		}
        	}
    	}
    	
    	tabDotForward.getItems().addAll(dataListNew);
    }
    
	private int dotNum(int i,int num) {
		List<ParamRule> addList = new ArrayList<>();
		for(ParamRule p: list) {
			if(i==p.getFrame().getValue().getIndex()) {
				if(Integer.parseInt(p.getData().getText())<=num){
					return 2;
				}
			}
		}
		
		return 1;
	}

    private List<DotDemo> writeDotConfig(int i,String device) {
    	List<DotDemo> dotListNew = new ArrayList<>();
    	int num=0;
    	for(DotDemo d : dotDemoList) {
    		if(String.valueOf(i).equals(d.getIndex()) && dotDemoList.get(num).getDevice().equals(device)) {
    			d.setNum(String.valueOf(num));
    			dotListNew.add(d);
    		}
    		num++;
    	}
		
    	return dotListNew;
    }
    
    private void dotConfig(int i,String device) {
    	tabDot.getItems().clear();
    	if(device == null) {
    		return;
    	}
    	List<DotDemo> dotListNew = new ArrayList<>();
    	int num=0;
    	for(DotDemo d : dotDemoList) {
    		if(String.valueOf(i).equals(d.getIndex()) && dotDemoList.get(num).getDevice().equals(device)) {
    			d.setNum(String.valueOf(num));
    			dotListNew.add(d);
    		}
			num++;
    	}
    	tabDot.getItems().addAll(dotListNew);
    }
    
}
