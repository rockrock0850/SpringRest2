package com.spring.rest.v2.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.spring.rest.v2.base.BaseController;
import com.spring.rest.v2.utils.Constant;


@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/demo")
public class BootstrapDemoController extends BaseController{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.
				currentRequestAttributes()).getRequest();
		super.init(request);
	}
	
	@RequestMapping(value = "/bootstrap", method = RequestMethod.GET)
	public @ResponseBody ModelAndView bootstrap(HttpServletRequest request) throws Exception{
		return new ModelAndView(Constant.BOOTSTRAP_DEMO);
	}
	
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public @ResponseBody ModelAndView image(HttpServletRequest request) throws Exception{
		return new ModelAndView(Constant.IMAGE_DEMO);
	}
	
	@RequestMapping(value = "/storeImage", method = RequestMethod.POST)
	public String storeImage(HttpServletRequest request, @RequestBody Map<String, Object> reqData) throws Exception{
	    String name = reqData.containsKey("name") ? (String) reqData.get("name") : "";
	    String extension = reqData.containsKey("extension") ? (String) reqData.get("extension") : "";
	    String baseFile = reqData.containsKey("baseFile") ? (String) reqData.get("baseFile") : "";
	    String data = baseFile.split(",")[1];// 解base64圖片需要先把資料前綴的[ data:image/gif;base64, ]字串拔掉才能正確解析
	    
	    File file = new File("D:\\temp\\" + name + "." + extension);
	    byte[] b = Base64.getDecoder().decode(data.getBytes());
	    InputStream inputStream = new ByteArrayInputStream(b);
	    FileUtils.copyInputStreamToFile(inputStream, file);
		
		return file.getAbsolutePath();
	}
	
	/*
	 * 1. 使用Spring+ajax的時候要注意req和res的資料型態是否一致, 
	 * 2. ajax: contentType( 傳後端的資料型態 ): 'application/json;charset=utf-8', dataType( 後端回傳的資料型態 ): 'application/json;charset=utf-8'
	 * 3. Spring: produces( 傳前端的資料型態 ) = "application/json;charset=utf-8"
	 * 4. 注意: 如果有設定produces參數為json的話如果不是回傳json的格式資料, 前端的ajax會觸發error function
	 * 5. 注意: 如果ajax有設定dataType參數為json的話, 後端若不是回傳json格式的資料會觸發error function
	 */
	
}
