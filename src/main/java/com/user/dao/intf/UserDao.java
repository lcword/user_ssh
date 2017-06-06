package com.user.dao.intf;

import java.util.List;

import com.user.dto.UserDto;
import com.user.model.User;
import com.user.page.Page;

public interface UserDao {
	void add(User user);
	void delete(String ids);
	void update(User user);
	List<User> query(Page<UserDto> page);
	User queryById(String id);
	int count(UserDto userDto);
	int dayCount(String date);
	boolean exist(User user);
	boolean checkName(String username);
}
