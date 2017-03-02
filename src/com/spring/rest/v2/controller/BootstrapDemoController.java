package com.spring.rest.v2.controller;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.spring.rest.v2.base.BaseController;
import com.spring.rest.v2.model.User;
import com.spring.rest.v2.service.UserService;
import com.spring.rest.v2.utils.Constant;
import com.spring.rest.v2.utils.Constant.Status;
import com.spring.rest.v2.utils.ShareTool;
import com.spring.rest.v2.vo.ResponseVO;
import com.spring.rest.v2.vo.form.UserCreateVO;
import com.spring.rest.v2.vo.form.UserUpdateVO;


@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/demo")
public class BootstrapDemoController extends BaseController{
	
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
	
}
