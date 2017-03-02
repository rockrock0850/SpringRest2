package com.spring.rest.v2.setting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSetting {
	/**
	 * 
	 * @return
	 */
	boolean isImg() default true;
}
