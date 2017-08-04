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
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession httpSession;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.
				currentRequestAttributes()).getRequest();
		super.init(request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ModelAndView list(HttpServletRequest request) throws Exception{
		List<User> userList = (List<User>) userService.select();
	    userList.forEach(user -> user.setImg(ShareTool.getImgSrc(baseUri, user.getImg())));
	    
		return new ModelAndView(Constant.USER_LIST, "userList", userList);
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ModelAndView login(HttpServletRequest request, @Valid User user, BindingResult result) throws Exception{
		if(StringUtils.equals(RequestMethod.GET.toString(), request.getMethod())){
			return new ModelAndView(Constant.USER_LOGIN);
		}
		
		if(result.hasErrors()){
			List<FieldError> errorList = result.getFieldErrors();
			return new ModelAndView(Constant.USER_LOGIN, "errorList", errorList);
		}
		
		if(userService.selectByLoginInfo(user) == null){
			return new ModelAndView(Constant.USER_LOGIN, "msg", Status.LOGIN_MSG001.getMessage());
		}

		httpSession.setAttribute("account", user.getAccount());
		httpSession.setAttribute("password", user.getPassword());

		return list(request);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody Object logout(HttpServletRequest request) throws Exception{
		if(httpSession.getAttributeNames().hasMoreElements()){
			httpSession.invalidate();
		}
		
		return list(request);
	}

	@RequestMapping(value = "/create", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ModelAndView create(HttpServletRequest request, @Valid UserCreateVO user, BindingResult result) throws Exception{
		if(StringUtils.equals(RequestMethod.GET.toString(), request.getMethod())){
			return new ModelAndView(Constant.USER_CREATE);
		}
		
		if(result.hasErrors()){
			List<FieldError> errorList = result.getFieldErrors();
			return new ModelAndView(Constant.USER_CREATE, "errorList", errorList);
		}

		if(userService.selectByAccount(user.getAccount()) != null){
			return new ModelAndView(Constant.USER_CREATE, "msg", Status.CREATE_USER_MSG001.getMessage());
		}
		
		if(!StringUtils.equals(user.getPassword(), user.getReplyPassword())){
			return new ModelAndView(Constant.USER_CREATE, "msg", Status.CREATE_USER_MSG002.getMessage());
		}

		File file = ShareTool.getUploadFile(baseDir, user.getFile(), user.getAccount());
		user.setImg(file == null ? "" : file.getAbsolutePath());
		
		if(userService.create(user) == null){
			return new ModelAndView(Constant.USER_CREATE, "msg", Status.CREATE_USER_MSG003.getMessage());
		}

		return new ModelAndView(Constant.USER_CREATE, "msg", Status.SUCCESS.getMessage());
	}

	@RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ModelAndView update(HttpServletRequest request, @Valid UserUpdateVO user, BindingResult result) throws Exception{
		if(!httpSession.getAttributeNames().hasMoreElements()){
			return new ModelAndView(Constant.USER_LOGIN);
		}
		
		if(StringUtils.equals(RequestMethod.GET.toString(), request.getMethod())){
			user.setAccount((String) httpSession.getAttribute("account"));
			return new ModelAndView(Constant.USER_UPDATE, "user", user);
		}

		if(result.hasErrors()){
			List<FieldError> errorList = result.getFieldErrors();
			return new ModelAndView(Constant.USER_UPDATE, "errorList", errorList);
		}
		
		if(userService.selectByLoginInfo(user) == null){
			return new ModelAndView(Constant.USER_UPDATE, "msg", Status.UPDATE_USER_MSG002.getMessage());
		}

		File file = ShareTool.getUploadFile(baseDir, user.getFile(), user.getAccount());
		user.setImg(file == null ? null : file.getAbsolutePath());
		user.setPassword(user.getNewPassword());
		
		if(userService.update(user) == null){
			return new ModelAndView(Constant.USER_UPDATE, "msg", Status.UPDATE_USER_MSG001.getMessage());
		}

		return new ModelAndView(Constant.USER_UPDATE, "msg", Status.SUCCESS.getMessage());
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody ModelAndView delete(HttpServletRequest request, @RequestParam(required = false) String account) throws Exception{
		if(!httpSession.getAttributeNames().hasMoreElements()){
			return new ModelAndView(Constant.USER_LOGIN);
		}
		
		if(StringUtils.equals(RequestMethod.GET.toString(), request.getMethod())){
			return new ModelAndView(Constant.USER_DELETE);
		}
		
		User user = userService.selectByAccount(account);
		if(user != null){
			File file = new File(user.getImg());
			if(file.exists()){
				file.delete();
			}
		}
		
		if(userService.deleteByAccount(account) == 0){
			return new ModelAndView(Constant.USER_DELETE, "msg", Status.DELETE_USER_MSG001.getMessage());
		}

		return new ModelAndView(Constant.USER_DELETE, "msg", Status.SUCCESS.getMessage());
	}

	/*
	 * Exception Handler111aaaa
	 */
	
	@Override
	@ExceptionHandler(Exception.class)
	public ResponseVO handleError(HttpServletRequest req, Exception exception) {
		return super.handleError(req, exception);
	}
	
}
