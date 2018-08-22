package com.pm.portal.controller.base;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;

@Controller
@PropertySource(value = "classpath:application.properties") // 指定配置文件
@RequestMapping(value = "/file")
public class FileController {

	@Value("${web.upload-path}")
	private String filePath;

	// private static final String filePath = "D:/file";

	@RequestMapping(value = "upload")
	@ResponseBody
	public Object upload(@RequestParam("head_img") MultipartFile file, HttpServletRequest request) {

		// file.isEmpty(); 判断图片是否为空
		// file.getSize(); 图片大小进行判断
		if (file.isEmpty()) {
			return Result.error(CodeMsg.UPLOAD_ERROR);
		}

		String name = request.getParameter("name");
		System.out.println("用户名：" + name);

		// 获取文件名
		String fileName = file.getOriginalFilename();
		System.out.println("上传的文件名为：" + fileName);

		// 获取文件的后缀名,比如图片的jpeg,png
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		System.out.println("上传的后缀名为：" + suffixName);

		// 文件上传后的路径
		fileName = UUID.randomUUID() + suffixName;
		System.out.println("转换后的名称:" + fileName);

		File dest = new File(filePath + fileName);

		try {
			file.transferTo(dest);
			return Result.success(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.UPLOAD_ERROR);
		}
	}

}
