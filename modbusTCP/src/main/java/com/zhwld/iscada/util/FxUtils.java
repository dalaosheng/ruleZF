package com.zhwld.iscada.util;

import java.io.InputStream;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author jzl
 *
 * @memo 显示界面等操作
 * 
 *       2017年10月12日
 */
public class FxUtils {
	private static Node node;

	/**
	 * 传入stage和fxml设置
	 * 
	 * @param stage
	 * @param fxml
	 * @param cls
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static Initializable replaceSceneContent(Stage stage, String fxml, Class cls, Double width, Double height)
			throws Exception {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = cls.getResourceAsStream(fxml);
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		loader.setLocation(cls.getResource(fxml));
		Parent page;
		try {
			page = (Parent) loader.load(in);
		} finally {
			in.close();
		}
		try {
			if (page != null) {
				node = page;

				Scene scene;
				if (width != null && height != null) {
					scene = new Scene(page, width, height);
				} else {
					scene = new Scene(page);
				}

				stage.setScene(scene);
				// stage.sizeToScene();
				return (Initializable) loader.getController();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static Node getNodeSceneContent() {
		return node;
	}

	public static Initializable replaceSceneContent(Stage stage, String fxml, Class cls) throws Exception {
		// 设置logo
		Logo.setDefaultLogo(stage);
		return replaceSceneContent(stage, fxml, cls, null, null);
	}

	/**
	 * 弹出确认对话框
	 * 
	 * @param message
	 */
	public static void alertInfo(String message) {

		alertInfo(null, message);

	}

	/**
	 * 弹出错误对话框
	 * 
	 * @param message
	 */
	public static void alertError(String message) {

		alertError(null, message);

	}
	
	/**
	 * 显示警告信息，默认不需要图标
	 * @param alertType 警告类型
	 * @param title 标题栏上的说明
	 * @param contentText 警告内容
	 */
	public static java.util.Optional<ButtonType> showAndWaitUtilityAlert(AlertType alertType,String title,String contentText)
	{
		Alert alert = new Alert(alertType,contentText);
		alert.setTitle(title);
		alert.initStyle(StageStyle.UTILITY);
		return alert.showAndWait();
	}
	

	/**
	 * 弹出警告对话框
	 * 
	 * @param message
	 */
	public static void alertWarning(String message) {

		alertWarning(null, message);

	}

	/**
	 * 弹出确认取消
	 * 
	 * @param message
	 * @return
	 */
	public static ButtonType alertConfirm(String message) {

		return alertConfirm(null, message);

	}

	/**
	 * 弹出确认对话框
	 * 
	 * @param message
	 */
	public static void alertInfo(String title, String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(FxUtils.class.getResource("images/AlertInformation.png").toString()));
			if (!StringUtils.isEmpty(title)) {
				alert.setTitle(title);
			}

			// alert.initStyle(StageStyle.UTILITY);
			alert.show();
		});
	}

	/**
	 * 弹出错误对话框
	 * 
	 * @param message
	 */
	public static void alertError(String title, String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR, message);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(FxUtils.class.getResource("images/AlertError.png").toString()));
			if (!StringUtils.isEmpty(title)) {
				alert.setTitle(title);
			}
			alert.show();
		});
	}

	public static void alertErrorConfigm(String message) {

		Alert alert = new Alert(Alert.AlertType.ERROR, message);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(FxUtils.class.getResource("images/AlertError.png").toString()));

		alert.setTitle("错误");

		alert.showAndWait();

	}

	/**
	 * 弹出警告对话框
	 * 
	 * @param message
	 */
	public static void alertWarning(String title, String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.WARNING, message);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(FxUtils.class.getResource("images/AlertWarnning.png").toString()));
			if (!StringUtils.isEmpty(message)) {
				alert.setTitle(title);
			}
			alert.show();
		});
	}

	/**
	 * 弹出确认取消
	 * 
	 * @param message
	 * @return
	 */
	public static ButtonType alertConfirm(String title, String message) {

		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, message);
		if (!StringUtils.isEmpty(title)) {
			confirmation.setTitle(title);
		}

		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(FxUtils.class.getResource("images/AlertConfirmation.png").toString()));

		Optional<ButtonType> result = confirmation.showAndWait();

		return result.get();

	}



}
