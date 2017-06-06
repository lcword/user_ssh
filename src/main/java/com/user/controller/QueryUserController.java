package com.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.user.dto.UserDto;
import com.user.model.User;
import com.user.page.Page;
import com.user.service.intf.UserService;

@Controller
public class QueryUserController {
	@Autowired
	private UserService userService;

	/* ��ѯ */
	@RequestMapping("query_user")
	public String query(Map<String,Object> map,UserDto userDto,String type,String curPageStr) {
		int allCount = userService.count(userDto);

		Page<UserDto> page = null;
		if (type != null) {
			int curPage = Integer.parseInt(curPageStr);
			page = new Page<UserDto>(allCount, curPage);
			if ("prePage".equals(type)) {
				/* ��һҳ */
				page.setCurPage(page.getPrePage());
			} else if ("nextPage".equals(type)) {
				/* ��һҳ */
				page.setCurPage(page.getNextPage());
			} else if ("turn".equals(type)) {
				/* ��ҳ ĩҳ ��ת */
				if (curPage < 1) {
					curPage = 1;
				} else if (curPage > page.getMaxPage()) {
					curPage = page.getMaxPage();
				}
				page.setCurPage(curPage);
			}
		} else {
			page = new Page<UserDto>(allCount, 1);
		}
		page.setT(userDto);

		/* ���ݲ�ѯ��������ѯ���� */
		List<User> userList = userService.query(page);
		/* ���ص����ݷ��������У��Դ��ݵ����� */
		map.put("userDto", userDto);
		map.put("user_list", userList);
		map.put("user_page", page);
		return "user_main";
	}
}
