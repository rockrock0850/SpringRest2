package com.spring.rest.v2.aspact;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.spring.rest.v2.utils.ShareTool;

@Controller
@Aspect
public class LogAspact {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Around("execution(* com.spring.rest.v2..*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		Object[] argList = joinPoint.getArgs();
		for(Object arg : argList){
			
			/*
			 *過濾掉不想Log出來的條件 
			 */
			
			if(arg == null || arg instanceof BindingResult || arg instanceof HttpServletRequest || 
					arg.getClass().getName().endsWith("Example")){
				continue;
			}

			log.info(className + "." + methodName + "() argument: " + ShareTool.toStringBuilder(arg));	
		}
		
		Object proceed = joinPoint.proceed();
		
		if(proceed != null){
			Object data = null;
			if(proceed instanceof ModelAndView){
				ModelAndView mv = (ModelAndView) proceed;
				Map<String, Object> map = mv.getModel();
				for(String key : map.keySet()){
					data = map.get(key);
					break;
				}
			}else{
				data = proceed;
			}
			
			log.info(className + "." + methodName + "() return: " + ShareTool.toStringBuilder(data));	
		}
		
		return proceed;
	}
	
	@AfterThrowing(
		      pointcut = "execution(* com.spring.rest.v2..*.*(..))",
		      throwing = "error")
	public void afterThrow(JoinPoint joinPoint, Throwable error){
		try {
		} catch (Throwable t) {
			log.error(t, t);
		}
	}
}
