package com.spring.rest.v2.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.spring.rest.v2.vo.FormExampleVO;

@Component
public class FormExampleValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return FormExampleVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormExampleVO vo = (FormExampleVO) target;
		String name = vo.getName();
		if(StringUtils.isBlank(name)){
			errors.rejectValue("name", null, "name can't be empty");
		}
	}

}
