package com.user.service.intf;

import java.util.List;

import com.user.dto.UserDto;
import com.user.model.User;
import com.user.page.Page;

public interface UserService {
	void delete(String ids);
	void update(User user,String path);
	List<User> query(Page<UserDto> page);
	int count(UserDto userDto);
	User queryById(String id);
	boolean exist(User user);
	boolean checkName(String username);
}
