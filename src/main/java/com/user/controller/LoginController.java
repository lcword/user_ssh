package com.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.user.model.User;
import com.user.service.intf.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	/* 登录 */
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request,HttpServletResponse response) {
		if (userService.exist(user)) {
			/* 用户名、密码验证通过 */
			request.getSession().setAttribute("login_user", user.getUsername());// 保存用户信息
			Cookie usernameCookie = new Cookie("login_username", user.getUsername());
			Cookie passwordCookie = new Cookie("login_password", user.getPassword());
			usernameCookie.setMaxAge(60 * 60);// 设置cookie保存期为1h，在保存期内无需经过登录界面
			passwordCookie.setMaxAge(60 * 60);
			response.addCookie(usernameCookie);
			response.addCookie(passwordCookie);
			return "redirect:index";
		} else {
			/* 获取cookie */
			Cookie[] cookies = request.getCookies();
			String login_username = "";
			String login_password = "";
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if ("login_username".equals(cookie.getName())) {
						login_username = cookie.getValue();
					}
					if ("login_password".equals(cookie.getName())) {
						login_password = cookie.getValue();
					}
				}
			}

			user = new User(login_username, login_password);
			if (userService.exist(user)) {
				/* 验证通过 */
				request.getSession().setAttribute("login_user", login_username);
				return "redirect:index";
			}
		}
		return "login";
	}

	/* 登出，默认清除cookie */
	@RequestMapping("/logout")
	public String logout(HttpServletResponse response) {
		Cookie usernameCookie = new Cookie("login_username", null);
		Cookie passwordCookie = new Cookie("login_password", null);
		usernameCookie.setMaxAge(0);
		passwordCookie.setMaxAge(0);
		response.addCookie(usernameCookie);
		response.addCookie(passwordCookie);
		return "redirect:login";
	}

	/* 主界面中转 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

}
