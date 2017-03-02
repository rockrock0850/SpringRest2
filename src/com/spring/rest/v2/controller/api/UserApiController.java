package com.spring.rest.v2.controller.api;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.v2.base.BaseController;
import com.spring.rest.v2.model.User;
import com.spring.rest.v2.service.UserService;
import com.spring.rest.v2.utils.Constant;
import com.spring.rest.v2.utils.Constant.Status;
import com.spring.rest.v2.utils.ShareTool;
import com.spring.rest.v2.vo.RequestVO;
import com.spring.rest.v2.vo.ResponseVO;
import com.spring.rest.v2.vo.form.UserCreateVO;
import com.spring.rest.v2.vo.form.UserUpdateVO;

@RestController
@Scope(value = "prototype")
@RequestMapping(value = "/api/user")
public class UserApiController extends BaseController{
	
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

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseVO login(HttpServletRequest request, @RequestBody @Valid RequestVO requestVO, BindingResult result) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(result.hasErrors()){
			List<FieldError> errorList = result.getFieldErrors();
			responseVO.setData(ShareTool.simpleFieldError(errorList));
			return responseVO;
		}
		
		User user = new ObjectMapper().readValue(requestVO.getData(), User.class);
		
		if(userService.selectByLoginInfo(user) == null){
			responseVO.setMsg(Status.LOGIN_MSG001.getMessage());
			return responseVO;
		}

		httpSession.setAttribute("account", user.getAccount());
		httpSession.setAttribute("password", user.getPassword());
		responseVO.setCode(Status.SUCCESS.getCode());
		responseVO.setMsg(Status.SUCCESS.getMessage());
		responseVO.setData(user);

		return responseVO;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody Object logout(HttpServletRequest request) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(httpSession.getAttributeNames().hasMoreElements()){
			httpSession.invalidate();
		}
		
		responseVO.setCode(Status.SUCCESS.getCode());
		responseVO.setMsg(Status.SUCCESS.getMessage());
		
		return responseVO;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")@SuppressWarnings("unchecked") 
	public @ResponseBody ResponseVO list() throws Exception{
		List<User> userList = (List<User>) userService.select();
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setCode(Status.SUCCESS.getCode());
		responseVO.setMsg(Status.SUCCESS.getMessage());
		responseVO.setData(userList);
		
		return responseVO;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO select(@PathVariable int id) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		Object user = userService.selectByPrimaryKey(id);
		if(user == null){
			responseVO.setMsg(Status.SELECT_USER_MSG001.getMessage());
			return responseVO;
		}
		
		responseVO.setCode(Status.SUCCESS.getCode());
		responseVO.setMsg(Status.SUCCESS.getMessage());
		responseVO.setData(user);
		
		return responseVO;
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO create(HttpServletRequest request, @RequestBody @Valid RequestVO requestVO, BindingResult result) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(result.hasErrors()){
			responseVO.setMsg(Status.FIELD_MSG001.getMessage());
			responseVO.setData(ShareTool.simpleFieldError(result.getFieldErrors()));
			
			return responseVO;
		}
		
		UserCreateVO user = new ObjectMapper().readValue(requestVO.getData(), UserCreateVO.class);
		
		if(userService.selectByAccount(user.getAccount()) != null){
			responseVO.setMsg(Status.CREATE_USER_MSG001.getMessage());
			
			return responseVO;
		}
		
		if(!StringUtils.equals(user.getPassword(), user.getReplyPassword())){
			responseVO.setMsg(Status.CREATE_USER_MSG002.getMessage());
			
			return responseVO;
		}

		File file = ShareTool.getUploadFile(baseDir, user.getFile(), user.getAccount());
		user.setImg(file == null ? "" : file.getAbsolutePath());

		if(userService.create(user) == null){
			responseVO.setMsg(Status.CREATE_USER_MSG003.getMessage());
			
			return responseVO;
		}
		
		responseVO.setCode(Status.SUCCESS.getCode());
		responseVO.setMsg(Status.SUCCESS.getMessage());
		responseVO.setData(user);

		return responseVO;
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO update(HttpServletRequest request, @RequestBody @Valid RequestVO requestVO, BindingResult result) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(!httpSession.getAttributeNames().hasMoreElements()){
			responseVO.setMsg(Status.DELETE_USER_MSG002.getMessage());
			
			return responseVO;
		}
		
		if(result.hasErrors()){
			responseVO.setMsg(Status.FIELD_MSG001.getMessage());
			responseVO.setData(ShareTool.simpleFieldError(result.getFieldErrors()));
			
			return responseVO; 
		}
		

		UserUpdateVO user = new ObjectMapper().readValue(requestVO.getData(), UserUpdateVO.class);
		
		if(userService.selectByLoginInfo(user) == null){
			responseVO.setMsg(Status.UPDATE_USER_MSG002.getMessage());
			
			return responseVO; 
		}

		File file = ShareTool.getUploadFile(baseDir, user.getFile(), user.getAccount());
		user.setImg(file == null ? null : file.getAbsolutePath());
		user.setPassword(user.getNewPassword());
		
		if(userService.update(user) == null){
			responseVO.setMsg(Status.UPDATE_USER_MSG001.getMessage());
			
			return responseVO; 
		}

		responseVO.setCode(Status.SUCCESS.getCode());
		responseVO.setMsg(Status.SUCCESS.getMessage());
		responseVO.setData(user);

		return responseVO;
	}
	
	@RequestMapping(value = "/{account}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO delete(@PathVariable String account) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(!httpSession.getAttributeNames().hasMoreElements()){
			responseVO.setMsg(Status.DELETE_USER_MSG002.getMessage());
			
			return responseVO;
		}
		
		User user = userService.selectByAccount(account);
		if(user != null){
			File file = new File(user.getImg());
			if(file.exists()){
				file.delete();
			}
		}
		
		if(userService.deleteByAccount(account) == 0){
			responseVO.setMsg(Status.DELETE_USER_MSG001.getMessage());
			
			return responseVO;
		}
		
		responseVO.setMsg(Status.SUCCESS.getMessage());
		responseVO.setData(account);

		return responseVO;
	}

	@RequestMapping(value = "/encrypt", method = RequestMethod.POST)
	public @ResponseBody ResponseVO encrypt(@RequestBody String data) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(ShareTool.encrypt(Constant.IV, Constant.KEY, data));
		
		return responseVO;
	}

	@RequestMapping(value = "/decrypt", method = RequestMethod.POST)
	public @ResponseBody ResponseVO decrypt(@RequestBody String data) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(ShareTool.decrypt(Constant.IV, Constant.KEY, data));
		
		return responseVO;
	}
	
	/*
	 * Call 別支API 時的情境
	 */

	@RequestMapping(value = "/userApi", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO listApi() throws Exception{
		return ShareTool.callApi(Constant.USER_API, "", "get");
	}

	@RequestMapping(value = "/userApi/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO selectApi(@PathVariable String id) throws Exception{
		ResponseVO responseVO = ShareTool.callApi(Constant.USER_API, id, "get");
		
		return responseVO;
	}

	@RequestMapping(value = "/userApi", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO createApi(@RequestBody @Valid RequestVO requestVO, BindingResult result) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(result.hasErrors()){
			responseVO.setMsg(Status.FIELD_MSG001.getMessage());
			responseVO.setData(ShareTool.simpleFieldError(result.getFieldErrors()));
			
			return responseVO;
		}
		responseVO = ShareTool.callApi(Constant.USER_API, ShareTool.toJsonStringBuilder(requestVO), "post");
		
		return responseVO;
	}

	@RequestMapping(value = "/userApi", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO updateApi(@RequestBody @Valid RequestVO requestVO, BindingResult result) throws Exception{
		ResponseVO responseVO = new ResponseVO();
		
		if(result.hasErrors()){
			responseVO.setMsg(Status.FIELD_MSG001.getMessage());
			responseVO.setData(ShareTool.simpleFieldError(result.getFieldErrors()));
			return responseVO; 
		}
		responseVO = ShareTool.callApi(Constant.USER_API, ShareTool.toJsonStringBuilder(requestVO), "put");
		
		return responseVO;
	}

	@RequestMapping(value = "/userApi/{account}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseVO deleteApi(@PathVariable String account) throws Exception{
		ResponseVO responseVO = ShareTool.callApi(Constant.USER_API, account, "delete");
		
		return responseVO;
	}
	
	/*
	 * Exception Handler
	 */
	
	@Override
	@ExceptionHandler(Exception.class)
	public ResponseVO handleError(HttpServletRequest req, Exception exception) {
		return super.handleError(req, exception);
	}
	
}
