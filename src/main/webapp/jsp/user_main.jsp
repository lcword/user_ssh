<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<style type="text/css">
	html,body {
	width:100%;
	height:100%;
	}
	table {
		margin: 0 auto;
		width:60%;
	}
	td,th {
		text-align: center;
	}
	a {
		text-decoration: none;
	}
	.picture {
		width:50px;
		height:50px;
	}
	#page_div,#select_div {
		text-align: center;
	}
	.age {
		width:2%;
	}
</style>
<script src="https://code.jquery.com/jquery-3.1.1.js" type="text/javascript"></script>
<script type="text/javascript">
	/* 批量删除 */
	function deleteAll() {
		var a = confirm("确认要删除？");
		if (a) {
			var names = $("input[name='checkbox']");
			var userIds = "";
			for (var i = 0; i < names.length; i++) {
				if (names[i].checked == true) {
					userIds += names[i].value + ",";
				}
			}
			if (userIds != "") {
				userIds = userIds.substring(0, userIds.length - 1);
				window.location.href = "delete_user?id="
						+ userIds;
			}
		}
	}
	/* 删除 */
	function deleteUser(id){
		var a = confirm("确认要删除？");
		var ids = id;
		if (a) {
			window.location.href = "delete_user?id="+ ids;
		}
	}
	/* 全选 */
	function allCheckbox() {
		var names = $("input[name='checkbox']");
		var allcheckbox = document.getElementById("allcheckbox");
		for (var i = 0; i < names.length; i++) {
			if (allcheckbox.checked == true) {
				names[i].checked = true;
			} else {
				names[i].checked = false;
			}
		}
	}
	/* 根据单个复选框的选择情况确定全选复选框是否被选中 */
	function selectSingle() {
		var k = 0;
		var oInput = document.getElementsByName("checkbox");
		for (var i = 0; i < oInput.length; i++) {
			if (oInput[i].checked == false) {
				k = 1;
				break;
			}
		}
		if (k == 0) {
			document.getElementById("allcheckbox").checked = true;
		} else {
			document.getElementById("allcheckbox").checked = false;
		}
	}
	/*跳转*/
	function turnPage(obj){
		var page = $(obj).val();
		window.location.href = "query_user?type=turn&&curPageStr="+page+"&&conditionType=${userDto.conditionType}&&condition=${userDto.condition}&&sex=${userDto.sex}&&minAge=${userDto.minAge}&&maxAge=${userDto.maxAge}";
	}
	/*查询全部*/
	function queryAll(){
		window.location.href = "query_user?conditionType=&&condition=&&sex=-1&&minAge=&&maxAge=";
	}
	$(function(){
		$("tr:even").css("background","#EEE5DE");
		$("tr:odd").css("background","#8B8682");
		$("tr:first").css("background","green");
	})
</script>
</head>
<body>
	<div id="select_div">
		<form action="query_user" method="post">
			<lable style="font-size:15px;">年龄</lable>
			<input name="minAge" value="${userDto.minAge}" class="age" />
			<lable> - </lable>
			<input name="maxAge" value="${userDto.maxAge}" class="age" />&nbsp;
			<lable style="font-size:15px;">性别</lable>
			<select name="sex">
				<c:if test="${userDto.sex == 1}">
					<option name="sex" value="-1">-</option>
					<option name="sex" value="1"  selected="selected">男</option>
					<option name="sex" value="0">女</option>
				</c:if>
				<%-- el表达式==也可以比较字符串的长度 --%>
				<c:if test="${userDto.sex == 0 and !userDto.sex == ''}">
					<option name="sex" value="-1">-</option>
					<option name="sex" value="1">男</option>
					<option name="sex" value="0"  selected="selected">女</option>
				</c:if>
				<c:if test="${userDto.sex == -1 or empty userDto.sex}">
					<option name="sex" value="-1" selected="selected">-</option>
					<option name="sex" value="1">男</option>
					<option name="sex" value="0">女</option>
				</c:if>
			</select>&nbsp;
			<c:if test="${userDto.conditionType == 'username'}">
				<select name="conditionType">
					<option name="conditionType" value="id">编号</option>
					<option name="conditionType" value="username" selected="selected">用户名</option>
				</select>
				<input id="condition" name="condition" placeholder="请输入查询条件" value="${userDto.condition }" />
			</c:if>
			<c:if test="${userDto.conditionType == 'id' or empty userDto.conditionType}">
				<select name="conditionType">
					<option name="conditionType" value="id" selected="selected">编号</option>
					<option name="conditionType" value="username">用户名</option>
				</select>
				<input id="condition" name="condition" placeholder="请输入查询条件" value="${userDto.condition }" />
			</c:if>
			<input type="submit" value="查询"/>
			<input type="button" value="查询全部" onclick="queryAll();"/>
		</form>
	</div>
	<br/>
	<table cellpadding="0" cellspacing="0" border="1">
		<tr>
			<th>全选<input type="checkbox" id="allcheckbox"
				onclick="allCheckbox()" /></th>
			<th>编号</th>
			<th>头像</th>
			<th>用户名</th>
			<th>密码</th>
			<th>性别</th>
			<th>年龄</th>
			<th colspan="2">操作</th>
		</tr>
		<c:forEach items="${user_list }" var="user">
			<tr>
				<td><input type="checkbox" name="checkbox"
					onclick="selectSingle();" value="${user.id }" /></td>
				<td>${user.id }</td>
				<td><img src="./picture/${user.pictureName}" alt="${user.pictureName}" class="picture" /></td>
				<td>${user.username }</td>
				<td>${user.password}</td>
				<td>
					<c:if test="${user.sex == 1}">男</c:if>
					<c:if test="${user.sex == 0}">女</c:if>
				</td>
				<td>${user.age}</td>
				<td><a href="delete_user?id=${user.id }" onclick="deleteUser(${user.id });">删除</a></td>
				<td><a href="pre_update_user?id=${user.id }">修改</a></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="10" style="background-color: green"><a href="#" onclick="deleteAll();">批量删除</a></td>
		</tr>
	</table>
	<br />
	<div id="page_div">
		<lable>总共${user_page.allCount }条</lable>
		<c:if test="${user_page.curPage > 1 }">
			<a href="query_user?type=turn&&curPageStr=1&&conditionType=${userDto.conditionType}&&condition=${userDto.condition}&&sex=${userDto.sex}&&minAge=${userDto.minAge}&&maxAge=${userDto.maxAge}">首页</a>
			<a href="query_user?type=prePage&&curPageStr=${user_page.curPage }&&conditionType=${userDto.conditionType}&&condition=${userDto.condition}&&sex=${userDto.sex}&&minAge=${userDto.minAge}&&maxAge=${userDto.maxAge}">上一页</a>
		</c:if>
		<c:if test="${user_page.curPage < user_page.maxPage }">
			<a href="query_user?type=nextPage&&curPageStr=${user_page.curPage }&&conditionType=${userDto.conditionType}&&condition=${userDto.condition}&&sex=${userDto.sex}&&minAge=${userDto.minAge}&&maxAge=${userDto.maxAge}">下一页</a>
			<a href="query_user?type=turn&&curPageStr=${user_page.maxPage}&&conditionType=${userDto.conditionType}&&condition=${userDto.condition}&&sex=${userDto.sex}&&minAge=${userDto.minAge}&&maxAge=${userDto.maxAge}">末页</a>
		</c:if>
		当前第<lable id="cur_page">${user_page.curPage }</lable>页
		跳转第<input style="width:15px;" value=""  onchange="turnPage(this);"/>页
		<lable>总共${user_page.maxPage }页</lable>
	</div>
</body>
</html>