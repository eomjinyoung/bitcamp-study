<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bitcamp.myapp.vo.Member"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>홈</title>
</head>
<body>
<div>
<div>
<%
Member member = (Member) session.getAttribute("loginUser");
if (member != null) {
    out.println("<p>" + member.getName() + "</p>");
    out.println("<a href='/auth/logout'>로그아웃</a>");
} else {
    out.println("<a href='/auth/login-form'>로그인</a>");
}
%>
</div>
<div>
    <h1>강의 관리 시스템</h1>
    <p>환영합니다!</p>
</div>
</div>
</body>
</html>