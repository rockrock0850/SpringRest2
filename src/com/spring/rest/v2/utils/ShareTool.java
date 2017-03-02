package com.spring.rest.v2.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.v2.vo.ResponseVO;
import com.spring.rest.v2.vo.SimpleFieldVO;

import net.sf.json.JSONObject;

public class ShareTool{
	
	private static Logger log = Logger.getLogger(ShareTool.class);
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public static String toStringBuilder(Object object){
		try {
			if(object instanceof String || object instanceof Map || 
					object instanceof Integer || object instanceof Double || object instanceof Long){
				return object.toString();
			}
			
			if(object instanceof List){
				List<?> list = (List<?>) object;
				
				if(list == null || list.isEmpty()){
					return "";
				}
				
				/*
				 * List 最多10筆
				 */
				int size = list.size() > 10 ? 10 : list.size();
				list = list.subList(0, size);
				
				List<String> dataList = new ArrayList<>();
				for(Object o : list){
					dataList.add(toStringBuilder(o));
				}

				return StringUtils.join(dataList, ", ");
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static String toJsonStringBuilder(Object object){
		JSONObject json = new JSONObject();
		try {
			json = JSONObject.fromObject(object);
		} catch (Exception e) {
			log.error(e, e);
		}
		return json.toString();
	}
	
	/**
	 * 
	 * @param prop
	 * @return
	 */
	public static String getProperty(String prop){
		return ResourceBundle.getBundle("system").getString(prop);
	}
	
	/**
	 * 
	 * @param file
	 * @param prop
	 * @return
	 */
	public static String getProperty(String file, String prop){
		return ResourceBundle.getBundle(file).getString(prop);
	}
	
	/**
	 * Base64 decode->AES decode
	 * 
	 * @param iv
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String iv, String key, String data) throws Exception{
		try {
			if(StringUtils.isBlank(iv) || StringUtils.isBlank(key) || StringUtils.isBlank(data)){
				return "";
			}
			
			byte[] decode = Base64.getDecoder().decode(data);
			
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] doFinal = cipher.doFinal(decode);
			
			data = new String(doFinal, "utf-8");
		} catch (Exception e) {
			log.error(e, e);
		}
		return data;
	}

	/**
	 * AES encode->Base64 encode
	 * 
	 * @param iv
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt(String iv, String key, String data){
		String encode = "";
		try {
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ciper.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] doFinal = ciper.doFinal(data.getBytes());//data.toString()
			
			encode = Base64.getEncoder().encodeToString(doFinal);
		} catch (Exception e) {
			log.error(e, e);
		}
		return encode;
	}

	/**
	 * 
	 * @param url
	 * @param data
	 * @param requestMethod
	 * @return
	 * @throws IOException
	 */
	public static ResponseVO callApi(String url, String data, String requestMethod){
		ResponseVO responseVO = new ResponseVO();
		try {
			URL obj = null;
			HttpURLConnection con = null;
			StringBuffer result = new StringBuffer();
			
			requestMethod = StringUtils.upperCase(requestMethod);
			switch (requestMethod) {
				case "POST":
					obj = new URL(url);
					con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod(requestMethod);
					con.setRequestProperty("Content-Type", "application/json");
					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.write(data.getBytes("utf-8"));
					wr.flush();
					wr.close();
					break;
					
				case "GET":
					obj = new URL(url + "/" + data);
					con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod(requestMethod);
					con.setRequestProperty("Content-Type", "application/json");
					break;
					
				case "PUT":
					obj = new URL(url);
					con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod(requestMethod);
					con.setRequestProperty("Content-Type", "application/json");
					con.setDoOutput(true);
					DataOutputStream wr2 = new DataOutputStream(con.getOutputStream());
					wr2.write(data.getBytes("utf-8"));
					wr2.flush();
					wr2.close();
					break;
					
				case "DELETE":
					obj = new URL(url + "/" + data);
					con = (HttpURLConnection) obj.openConnection();
					con.setRequestMethod(requestMethod);
					con.setRequestProperty("Content-Type", "application/json");
					break;
			}
		
			int responseCode = con.getResponseCode();
			log.info("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				result.append(inputLine);
			}
			in.close();
			
			responseVO = new ObjectMapper().readValue(result.toString(), ResponseVO.class);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return responseVO;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static List<SimpleFieldVO> simpleFieldError(List<FieldError> list){
		List<SimpleFieldVO> fieldList = new ArrayList<>();
		try {
			for(FieldError fieldError : list){
				SimpleFieldVO field = new SimpleFieldVO();
				field.setField(fieldError.getField());
				field.setMessage(fieldError.getDefaultMessage());
				fieldList.add(field);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return fieldList;
	}
	
	/**
	 * 
	 * @param multipartFile
	 * @param account
	 * @return
	 */
	public static File getUploadFile(String root, MultipartFile multipartFile, String account){
		try {
			if(multipartFile != null && !multipartFile.isEmpty()){
				String[] retvals = multipartFile.getOriginalFilename().split("\\.");
				File file = new File(root + Constant.IMG_ROOT + "/" +  account + "." + retvals[1]);
				if (!file.exists()) {
					file.mkdirs();
				}
				multipartFile.transferTo(file);
				return file;
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	public static String getImgSrc(String baseUri, String img){
		String src = "";
		try {
			if(StringUtils.isNotBlank(img)){
				src = baseUri + img.substring(img.indexOf("\\resources"));	
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return src;
	}

	/**
	 * 
	 * @param vo
	 */
	public static void sendMail(Object vo) {
		String to = "";// 收件人電子郵箱
		String from = "service@everywhere.com.tw";// 發件人電子郵箱
		String host = "localhost";// 指定發送郵件的主機為localhost
		String content = String.format("<p>親愛的 %s 您好：<br />感謝您申請錢老闆雲端平台帳號! 請靜候管理者審核。<br/>-------------------------------------------------------------<br/>若會員非您本人申請加入，請您與管理者聯繫<br/>預祝您愉快！<br/>※此信件為系統發出信件，請勿直接回覆。<br/>如果您有任何疑問，歡迎管理者聯繫，我們將竭誠為您服務 再次感謝您對錢老闆雲端平台的支持與愛護<br/>-------------------------------------------------------------<br/>錢老闆雲端平台<br/>116 臺北市信義區松隆路 102 號 17 樓<br/>Tel: 0800-003387<br/>E-mail: service@sbcs.com.tw<br/>http://www.sbcs.com.tw<br/></p>", "");

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("EveryWhere","UTF-8");
			message.setContent(content, "text/html;charset=UTF-8");
			Transport.send(message);
			log.debug("Sent message successfully....");
		} catch (MessagingException e) {
			log.error(e, e);
		}
	}
}
