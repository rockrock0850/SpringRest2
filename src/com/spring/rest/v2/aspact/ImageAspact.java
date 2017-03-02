package com.spring.rest.v2.aspact;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.google.common.net.MediaType;
import com.spring.rest.v2.model.User;
import com.spring.rest.v2.setting.ValidSetting;
import com.spring.rest.v2.utils.ShareTool;
import com.spring.rest.v2.vo.form.UserCreateVO;
import com.spring.rest.v2.vo.form.UserUpdateVO;

@Controller
@Aspect
public class ImageAspact {
	@Before("execution(* com.spring.rest.v2.controller..*.*(..))")
	public void before(JoinPoint joinPoint) throws Exception{
		User user = null;
		BindingResult result = null;
		HttpServletRequest request = null;
		
		Object[] argList = joinPoint.getArgs();
		for(Object arg : argList){
			if (arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
			}
			
			if (arg instanceof UserCreateVO || arg instanceof UserUpdateVO) {
				user = (User) arg;
			} 
			
			if (arg instanceof BindingResult) {
				result = (BindingResult) arg;
			}
		}
		
		if(user != null && result != null){
			Field field = user.getClass().getSuperclass().getDeclaredField("file");
			ValidSetting validSetting = field.getDeclaredAnnotation(ValidSetting.class);
		    String root = request.getSession().getServletContext().getRealPath("").
		    		replace(".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp3\\wtpwebapps\\", "");
			File file = ShareTool.getUploadFile(root, user.getFile(), user.getAccount());
			if(validSetting.isImg() && file != null){
				String type = Files.probeContentType(Paths.get(file.getAbsolutePath())).split("/")[0];
				if(!StringUtils.equals(type, MediaType.ANY_IMAGE_TYPE.type())){
					FieldError fieldError = new FieldError(user.getClass().getSimpleName(), "file", "請選擇圖片類型的檔案");
					result.addError(fieldError);
					file.delete();
				}
			}
		}
	}
}
