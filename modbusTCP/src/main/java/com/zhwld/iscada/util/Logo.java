package com.zhwld.iscada.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Logo {
	private static Image image;
	
	public static void setDefaltLogoFileName(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			try {
				image = new Image(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(fileName + "：LOGO文件不存在！");
		}
	}
	
	public static void setDefaultLogo(Stage stage) {
		if (stage != null && image != null)
			stage.getIcons().add(image);
	}
	
	public static void setStageIcon(Stage stage,String fileName)
	{
		try {
			File file = new File(fileName);
			if (file.exists()) {
				try {
					Image image = new Image(new FileInputStream(file));
					if (stage != null && image != null)
					{
						stage.getIcons().add(image);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(fileName + ":文件不存在！");
			}
		} catch (Exception e) {
			System.out.println(fileName + ":文件不存在！");
			e.printStackTrace();
		}
	}
}
