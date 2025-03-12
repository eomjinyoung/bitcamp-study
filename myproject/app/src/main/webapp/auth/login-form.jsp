<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
<div>
    <h2>로그인</h2>
    <form action="/auth/login" method="post">
        <div>
            <label for="email">이메일:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
            <label for="password">암호:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <input type="submit" value="로그인">
        </div>
    </form>
</div>
</body>
</html>