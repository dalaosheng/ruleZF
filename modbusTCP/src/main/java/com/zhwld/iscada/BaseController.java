package com.zhwld.iscada;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class BaseController implements Initializable {

	public BaseController() {

	}

	/**
	 * 当前Scene的stage
	 */
	private Stage stage;

	public void setStage(Stage stage) {


		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	String getPropertyValue(String path, String propertyName) {
		String propValue = "";
		Properties prop = new Properties();

		InputStream inputStream = null;
		try {

			inputStream = new FileInputStream(path);
			if (inputStream != null) {
				prop.load(inputStream);

				propValue = prop.getProperty(propertyName);
			}

		} catch (Exception ex) {

		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块

				}
		}

		return propValue;
	}
}
