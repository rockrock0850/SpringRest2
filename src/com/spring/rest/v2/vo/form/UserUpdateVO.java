package com.spring.rest.v2.vo.form;

import org.hibernate.validator.constraints.NotBlank;

import com.spring.rest.v2.model.User;

public class UserUpdateVO extends User{
    @NotBlank(message = "新密碼欄位不可以是空值")
    private String newPassword;

    /*
     * 
     */

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}