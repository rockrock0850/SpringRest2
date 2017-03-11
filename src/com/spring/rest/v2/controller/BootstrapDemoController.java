package com.spring.rest.v2.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
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
