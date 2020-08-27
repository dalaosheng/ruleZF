package com.zhwld.iscada.client;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesUtil {

	/**
	 * 获取工作空间路径
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getProjectPath(Class cls) {

		java.net.URL url = cls.getProtectionDomain().getCodeSource().getLocation();
		String filePath = null;
		String path = null;
		try {
			filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块

		}

		if (filePath != null) {
			File file = new File(filePath);
			if (file != null && file.getParentFile() != null) {
				path = file.getParent();
			}
		}
		return path;
	}

	/**
	 * 获取classpath目录上上级目录
	 * 
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static String getProjectPathParent(Class cls) {
		String path = getProjectPath(cls);
		File file = new File(path);
		if (file.exists()) {
			if (file.getParentFile() != null && file.getParentFile().exists()) {
				path = file.getParent();
			}

		}

		return path;
	}



}
