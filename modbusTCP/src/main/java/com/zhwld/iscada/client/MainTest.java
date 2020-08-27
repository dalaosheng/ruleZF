package com.zhwld.iscada.client;


import java.io.File;

import com.zhwld.iscada.client.controller.IndexController;
import com.zhwld.iscada.util.FxUtils;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		IndexController indexController = null;
		
		try {
			indexController = (IndexController) FxUtils.replaceSceneContent(primaryStage, "fxml/Index.fxml",
					IndexController.class);
			String path = PropertiesUtil.getProjectPath(this.getClass())+"/css/rule.css";
			File file = new File(path);
			Application.setUserAgentStylesheet(file.toURI().toURL().toExternalForm());
			indexController.setStage(primaryStage);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});
			String path2 = PropertiesUtil.getProjectPath(this.getClass())+File.separator+"images"+File.separator+"timg.jpg";
			Logo.setDefaltLogoFileName(PropertiesUtil.getProjectPath(this.getClass()) + "/images/timg.jpg");
			Logo.setDefaultLogo(primaryStage);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FxUtils.alertError(e.getMessage());
		}
		
//		primaryStage.getIcons().add(new Image("timg.jpg"));

		
		if (indexController != null) {
			primaryStage.setTitle("4G模块规约配置工具");
			primaryStage.setIconified(false);
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
