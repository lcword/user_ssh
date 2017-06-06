package com.user.model;

public class User {
	private String id;//��ţ����ݿ��Զ�����
	private String username;//�û���
	private String password;//����
	private int sex;//�Ա�
	private int age;//����
	private String pictureName;//ͷ����ļ����ƣ�����������
	public User() {
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public User(String id, String username,String password, int sex, int age) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.age = age;
	}
	public User(String id, String username, String password, int sex, int age, String pictureName) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.pictureName = pictureName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", sex=" + sex + ", age=" + age + ", pictureName="
				+ pictureName + "]";
	}
}
