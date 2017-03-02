package com.spring.rest.v2.aspact;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Controller;

import com.spring.rest.v2.setting.SecretSetting;
import com.spring.rest.v2.utils.ShareTool;
import com.spring.rest.v2.vo.RequestVO;

@Controller
@Aspect
public class DecryptAspact {
	@Before("execution(* com.spring.rest.v2.controller.api..*.*(..))")
	public void before(JoinPoint joinPoint) throws Exception{
		Object[] argList = joinPoint.getArgs();
		for(Object arg : argList){
			if(arg instanceof RequestVO){
				RequestVO requestVO = (RequestVO) arg;
				Field field = requestVO.getClass().getDeclaredField("data");
				SecretSetting secretSetting = field.getAnnotation(SecretSetting.class);
				if(secretSetting.decrypt()){
					String iv = ShareTool.getProperty("aes.iv");
					String key = ShareTool.getProperty("aes.key");
					requestVO.setData(ShareTool.decrypt(iv, key, requestVO.getData()));
				}
			}
		}
	}
}
