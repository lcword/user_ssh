package com.user.dto;

public class UserDto {
	private String conditionType;
	private String condition;
	private String sex;
	private String minAge;
	private String maxAge;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		if ("-1".equals(sex)) {
			this.sex = null;
		}else{
			this.sex = sex;
		}
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getMinAge() {
		return minAge;
	}
	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}
	public String getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}
	@Override
	public String toString() {
		return "UserDto [conditionType=" + conditionType + ", condition=" + condition + ", sex=" + sex + ", minAge="
				+ minAge + ", maxAge=" + maxAge + "]";
	}
}
