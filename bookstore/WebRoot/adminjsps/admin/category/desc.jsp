<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {background: rgb(254,238,189);}
</style>
  </head>
  
  <body>
    <h1>分类详情</h1>
    <p style="font-weight: 900; color: red">${msg }</p>
    <form action="<c:url value='/admin/CategoryServlet'/>" method="post">
    	<input type="hidden" name="cid" value="${category.cid }"/>
    	分类名称：<input type="text" name="cname" value="${category.cname }"/>
    	<input type="submit" value="mod" name="method"/>
    	<input type="submit" value="del"name="method"/>
    </form>
  </body>
</html>

