package com.zhwld.iscada.client.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.zhwld.iscada.BaseController;
import com.zhwld.iscada.client.BaseRule;
import com.zhwld.iscada.client.DotDemo;
import com.zhwld.iscada.client.PropertiesUtil;
import com.zhwld.iscada.util.FxUtils;

public class IEC101Controller extends BaseController {
	
	@FXML
	private TableView<BaseRule> tabBaseRule;
	@FXML
	private Button IEC101Exit;
	@FXML
	private Button baseConfirm;
	@FXML
	private Button baseParam;
	
	private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initBasicTable();
	}
	
private void initBasicTable() {
    	
    	TableColumn<BaseRule, String> paramTypeColumn = new TableColumn<BaseRule, String>("参数类型");
    	paramTypeColumn.setCellValueFactory(new PropertyValueFactory<BaseRule, String>("paramType"));
		
		TableColumn<BaseRule, ComboBox> paramValueColumn = new TableColumn<BaseRule, ComboBox>("参数值");
		paramValueColumn.setCellValueFactory(new PropertyValueFactory<BaseRule, ComboBox>("paramValue"));
		
		tabBaseRule.getColumns().addAll(paramTypeColumn,paramValueColumn);
		
		List<BaseRule> dataList = BaseRule.getDataList();
		tabBaseRule.getItems().addAll(dataList);
		
		baseParam.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {	
		    	tabBaseRule.getItems().clear();
		    	List<BaseRule> saveList2 = BaseRule.getDataList();
				tabBaseRule.getItems().addAll(saveList2);
		    }
		});
		
		baseConfirm.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {	
		    	List<Map<String, Object>> listJson3 = new ArrayList<Map<String, Object>>(); 
		    	Map<String, Object> map = new HashMap<String, Object>();
		    	map.put("rule","IEC101");
				map.put("ip",dataList.get(0).getParamValue().getValue().toString());
				map.put("port",dataList.get(1).getParamValue().getValue().toString());
				map.put("addr",dataList.get(2).getParamValue().getValue().toString());
				listJson3.add(map); 

		    	mapPrintln(listJson3);
		    	listJson3.clear();
			    if(listJson3 != null)
				{
					FxUtils.alertInfo("保存成功");
					return;
				}else {
					
					return;
				}
		    }
		});
		
		IEC101Exit.setOnAction(new EventHandler<ActionEvent>() {
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

	private static void mapPrintln(List<Map<String, Object>> list) {
	    if (list == null && list.size() == 0) {
	        return;
	    }
	    String path = PropertiesUtil.getProjectPath(IEC101Controller.class)+("/json/config1.json");
	    Gson gson = new Gson();
	    String jsonString =gson.toJson(list);
	    String jsonNewString = "{\"" + "zfRule" + "\":[" + jsonString.substring(1,jsonString.length()) + ",";//第一次导入
	    WriteConfigJson(jsonNewString,path);	
	    System.out.println(jsonNewString);
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
	
}

