package com.spring.rest.v2.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.rest.v2.service.UserService;
import com.spring.rest.v2.utils.Constant;
import com.spring.rest.v2.utils.ShareTool;
import com.spring.rest.v2.vo.RequestVO;
import com.spring.rest.v2.vo.form.UserCreateVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config.xml")
public class UserApiControllerTest{
	@Autowired
	UserService service;

	@Test
	public void list() throws Exception {
		Assert.assertNotNull(service.select());
	}

	@Test
	public void select() throws Exception {
		Assert.assertNotNull(service.selectByPrimaryKey(4));
	}

	@Test
	public void create() throws Exception {
		UserCreateVO user = new UserCreateVO();
		user.setAccount("admin4");
		user.setPassword("admin4");
		user.setReplyPassword("admin4");

		Assert.assertNotNull(service.create(user));
	}

	@Test
	public void update() throws Exception {
		UserCreateVO user = new UserCreateVO();
		user.setAccount("admin4");
		user.setPassword("admin4");
		user.setReplyPassword("admin4");

		Assert.assertNotNull(service.update(user));
	}

	@Test
	public void delete() throws Exception {
		Assert.assertNotNull(service.deleteByAccount("admin4"));
	}

	@Test
	public void listApi() throws IOException {
		Assert.assertNotNull(ShareTool.callApi(Constant.USER_API, "", "get"));
	}

	@Test
	public void selectApi() throws IOException {
		Assert.assertNotNull(ShareTool.callApi(Constant.USER_API, "4", "get"));
	}

	@Test
	public void createApi() throws IOException {
		RequestVO requestVO = new RequestVO();
		requestVO.setData("{\"account\":\"admin4\",\"password\":\"admin4\",\"replyPassword\":\"admin4\"}");

		Assert.assertNotNull(ShareTool.callApi(Constant.USER_API, ShareTool.toJsonStringBuilder(requestVO), "post"));
	}

	@Test
	public void updateApi() throws IOException {
		RequestVO requestVO = new RequestVO();
		requestVO.setData("{\"account\":\"admin4\",\"password\":\"admin4\",\"newPassword\":\"admin4\"}");

		Assert.assertNotNull(ShareTool.callApi(Constant.USER_API, ShareTool.toJsonStringBuilder(requestVO), "put"));
	}

	@Test
	public void deleteApi() throws IOException {
		Assert.assertNotNull(ShareTool.callApi(Constant.USER_API, "admin4", "delete"));
	}
}
