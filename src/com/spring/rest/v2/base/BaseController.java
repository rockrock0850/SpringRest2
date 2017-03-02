package com.spring.rest.v2.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.spring.rest.v2.utils.Constant.Status;
import com.spring.rest.v2.vo.ResponseVO;

public class BaseController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/*
	 * 
	 */
	
	protected String baseDir;
	protected String baseUri;
	
	/**
	 * 
	 * @param request
	 * @param txManager
	 */
	public void init(HttpServletRequest request){
		String url = request.getRequestURL().toString();
		this.baseUri = url.replace(request.getServletPath(), "");
		this.baseDir = request.getSession().getServletContext().getRealPath("").
				replace(".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp3\\wtpwebapps\\", "");
	    System.setProperty("baseDir", baseDir);
	}
	
	/**
	 * 
	 * @param req
	 * @param exception
	 * @return
	 */
	public ResponseVO handleError(HttpServletRequest req, Exception e) {
		/*ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("error");*/
		 
		ResponseVO responseVO = new ResponseVO();
		responseVO.setCode(Status.UNKNOWN_ERROR.getCode());
		responseVO.setMsg(Status.UNKNOWN_ERROR.getMessage());
		responseVO.setData(new StringBuilder("Request: " + req.getRequestURL() + ", Throws ****" + e.getMessage() + "****"));
		log.error(e, e);
		
		return responseVO;
	}
}
