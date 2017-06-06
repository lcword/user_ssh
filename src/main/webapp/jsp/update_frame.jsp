<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新用户信息</title>
<style type="text/css">
	.error,span {
		color:red;
	}
</style>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("#user_form").validate({
			rules:{
				username:{
					required:true,
				},
				password:"required",
				age:{
					required:true,
					digits:true,
					range:[0,100]
				},
			},
			messages:{
				username:"用户名不能为空!",
				password:"密码不能为空!",
				age:{
					required:"年龄不能为空!",
					digits:"年龄只能是数字!"
				},
			}
			
		});
		$("#username").blur(function(){
			 $.ajax({
				url:"check_username",
				data:"username=" + $(this).val(),
				dataType:"json",
				success:function(result){
					if(result.message == "yes"){
						$("span").empty();
						$("#username").after("<span>用户名已存在!</span>");
					}else{
						$("span").empty();
					}
				}
			}); 
		});
	});
</script>
</head>
<body>
	<form action="update_user" method="post" id="user_form" enctype="multipart/form-data">
		<table>
			<input type="hidden" name="id" value="${user.id }" />
			<tr>
				<td>头像</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td>用户名</td>
				<td><input id="username" name="username" value="${user.username}" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" name="password" value="${user.password}" /></td>
			</tr>
			<tr>
				<td>性别</td>
				<c:if test="${empty user || user.sex == 1}">
					<td>男<input type="radio" name="sex" value="1" checked="checked"/> 女<input type="radio" name="sex" value="0"/></td>
				</c:if>
				<c:if test="${user.sex == 0}">
					<td>男<input type="radio" name="sex" value="1"/> 女<input type="radio" name="sex" value="0"  checked="checked"/></td>
				</c:if>
			</tr>
			<tr>
				<td>年龄</td>
				<td><input name="age" value="${user.age}" /></td>
			</tr>
		</table>
		<input type="submit" value="确定" />
		<input type="reset" value="重置" />
	</form>
</body>
</html>