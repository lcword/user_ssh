package com.user.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.user.model.User;
import com.user.service.intf.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	/* 预-增加 */
	@RequestMapping("pre_add_user")
	public String pre_add() {
		return "update_frame";
	}

	/* 预-修改 */
	@RequestMapping("pre_update_user")
	public String pre_update(User user,Map<String,User> map) {
		map.put("user", userService.queryById(user.getId()));
		return "update_frame";
	}

	/* 增加或者修改 */
	@RequestMapping("update_user")
	public String update(User user,MultipartFile file,HttpServletRequest request) {
		String path = request.getServletContext().getRealPath("/picture");
		if (file.getSize() > 0) {
			/* 头像 */
			File fatherFile = new File(path);
			if (!fatherFile.exists()) {
				fatherFile.mkdirs();
			}
			String name = String.valueOf(new Date().getTime()) + ".jpg";
			File saveFile = new File(fatherFile, name);
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			user.setPictureName(name);
			System.out.println("path:" + path);
			System.out.println("name:" + name);
		} else {
			user.setPictureName(null);
		}
		userService.update(user, path);
		return "redirect:q uery_user";
	}

	/* 删除 */
	@RequestMapping("delete_user")
	public String delete(User user) {
		userService.delete(user.getId());
		return "redirect:query_user";
	}

	/* 检查用户名是否相同 */
	@RequestMapping("check_username")
	public void checkUserName(String username,PrintWriter pw) {
		boolean flog = userService.checkName(username);
		if (flog) {
			pw.print("{\"message\":\"yes\"}");
		} else {
			pw.print("{\"message\":\"no\"}");
		}
	}

}
