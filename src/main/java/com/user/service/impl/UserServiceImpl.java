package com.user.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.user.dao.intf.UserDao;
import com.user.dto.UserDto;
import com.user.model.User;
import com.user.page.Page;
import com.user.service.intf.UserService;


public class UserServiceImpl implements UserService{
	private UserDao userDao;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void delete(String ids) {
		userDao.delete(ids);
	}

	@Override/*���ӻ����޸�*/
	public void update(User user,String path) {
		if(user.getId() != null && !user.getId().isEmpty()){
			/*�޸�*/
			
			/*��ȡ��һ�ε�ͷ������*/
			User u = userDao.queryById(user.getId());
			String name = u.getPictureName();
			if(user.getPictureName() != null && !user.getPictureName().isEmpty()){
				/*�޸ĵ�ʱ���ϴ���ͷ�����֮ǰ�Ѿ��ϴ���ͷ����ôɾ��ԭ�������ͷ��ͼƬ*/
				File file = new File(path + "/" + name);
				if(file.exists()){
					file.delete();
				}
			}else{
				/*�޸ĵ�ʱ��û���ϴ�ͷ��������һ���ϴ���ͷ��*/
				user.setPictureName(name);
			}
			
			/*ʹ������״̬�Ķ�����³־û�״̬�Ķ���*/
			u.setId(user.getId());
			u.setUsername(user.getUsername());
			u.setAge(user.getAge());
			u.setPassword(user.getPassword());
			u.setPictureName(user.getPictureName());
			
			userDao.update(u);
		}else{
			/*����*/
			String date = dateFormat.format(new Date());
			int count = userDao.dayCount(date);
			String num = "";
			if(++count < 100){
				num += "0";
			}
			if(count < 10){
				num += "0";
			}
			user.setId(date + num + String.valueOf(count));
			userDao.add(user);
		}
	}

	@Override
	public List<User> query(Page<UserDto> page) {
		return userDao.query(page);
	}

	@Override
	public int count(UserDto userDto) {
		return userDao.count(userDto);
	}

	@Override
	public User queryById(String id) {
		return userDao.queryById(id);
	}

	@Override
	public boolean exist(User user) {
		return userDao.exist(user);
	}

	@Override
	public boolean checkName(String username) {
		return userDao.checkName(username);
	}

}
