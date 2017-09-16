package com.spring.rest.v2.utils;

public class Constant {
	
	/*
	 *  
	 */
	
	public final static String IV = ShareTool.getProperty("aes.iv");
	public final static String KEY = ShareTool.getProperty("aes.key");
	public final static String IMG_ROOT = ShareTool.getProperty("img.root");
	
	/*
	 * Api URI
	 */
	
	public final static String USER_API = ShareTool.getProperty("domain") + "/SpringRest/api/user";
	
	/*
	 * Jsp path
	 */
	
	public final static String USER_LIST = "user/user-list";
	public final static String USER_LOGIN = "user/user-login";
	public final static String USER_CREATE = "user/user-create";
	public final static String USER_UPDATE = "user/user-update";
	public final static String USER_DELETE = "user/user-delete";
	public final static String BOOTSTRAP_DEMO = "demo/bootstrap";
	public final static String IMAGE_DEMO = "demo/image";
	
	/*
	 * 系統狀態
	 */
	
	public enum Status { 
		SELECT_USER_MSG001("無此使用者"),
		
		CREATE_USER_MSG001("帳號已存在"),
		CREATE_USER_MSG002("請確認密碼是否一致"),
		CREATE_USER_MSG003("註冊失敗"),
		
		DELETE_USER_MSG001("刪除失敗"),
		DELETE_USER_MSG002("請登入"),

		UPDATE_USER_MSG001("更新失敗"),
		UPDATE_USER_MSG002("密碼錯誤"),
		
		LOGIN_MSG001("錯誤的帳號或密碼"),
		
		FIELD_MSG001("必要欄位不足"), 
		
		EXCEPTION_RECORD_MSG001("無此錯誤紀錄"),
		EXCEPTION_RECORD_MSG002("新增失敗"),
		
		SUCCESS("000", "執行成功"),
		UNKNOWN_ERROR("999", "未知的錯誤");
		
		private String code;
		private String message;
		
		private Status(String message) {
			this.message = message;
		}
		
		private Status(String code, String message) {
			this.code = code;
			this.message = message;
		}
		
		/*
		 * setter getter
		 */
		
		public String getMessage() {
			return message;
		}

		public String getCode() {
			return code;
		}
	}
}
