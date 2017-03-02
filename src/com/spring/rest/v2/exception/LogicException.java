package com.spring.rest.v2.exception;

import org.apache.commons.lang3.StringUtils;

import com.spring.rest.v2.utils.Constant.Status;

public class LogicException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 328203399395350605L;
	
	private String code;
	private String errorMessage;
	private String causeMessage;

	public LogicException() {
		super();
	}

	public LogicException(Status status) {
		super(status.name() + ":" + status.getMessage());
		this.code = status.name();
		this.errorMessage = status.getMessage();
	}

	public LogicException(String code, String errorMessage) {
		super(code + ":" + errorMessage);
		this.code = code;
		this.errorMessage = errorMessage;
	}

	public LogicException(Throwable t) {
		super("9999:" + t.getMessage(), t);
		this.code = "9999";
		this.causeMessage = t.getMessage();
	}

	public LogicException(String errorMessage, Throwable t) {
		super("9999:" + errorMessage, t);
		this.code = "9999";
		this.errorMessage = errorMessage;
		this.causeMessage = t.getMessage();
	}

	public String getCode() {
		return code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getCauseMessage() {
		return causeMessage;
	}

	public String toString() {
		return this.getClass().getSimpleName() + ":" + (StringUtils.isNotBlank(this.code) ? "[code]=" + this.code : "")
				+ (StringUtils.isNotBlank(this.errorMessage) ? ", [errorMessage]=" + this.errorMessage : "")
				+ (StringUtils.isNotBlank(this.causeMessage) ? ", [causeMessage]=" + this.causeMessage : "");
	}
}