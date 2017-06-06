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

	/* 查询 */
	@RequestMapping("query_user")
	public String query(Map<String,Object> map,UserDto userDto,String type,String curPageStr) {
		int allCount = userService.count(userDto);

		Page<UserDto> page = null;
		if (type != null) {
			int curPage = Integer.parseInt(curPageStr);
			page = new Page<UserDto>(allCount, curPage);
			if ("prePage".equals(type)) {
				/* 上一页 */
				page.setCurPage(page.getPrePage());
			} else if ("nextPage".equals(type)) {
				/* 下一页 */
				page.setCurPage(page.getNextPage());
			} else if ("turn".equals(type)) {
				/* 首页 末页 跳转 */
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

		/* 根据查询的条件查询数据 */
		List<User> userList = userService.query(page);
		/* 返回的数据放在属性中，以传递到界面 */
		map.put("userDto", userDto);
		map.put("user_list", userList);
		map.put("user_page", page);
		return "user_main";
	}
}
