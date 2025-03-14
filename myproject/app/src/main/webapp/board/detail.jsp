<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 조회</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .board-form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 700px;
        }

        .board-form-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        .form-group input[type="text"],
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
            font-size: 16px;
        }

        .form-group textarea {
            height: 200px;
            resize: vertical; /* Allow vertical resizing of the textarea */
        }

        .form-group input[type="submit"] {
            background-color: #5cb85c;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
            font-size: 16px;
        }

        .form-group input[type="submit"]:hover {
            background-color: #4cae4c;
        }

        .form-group input[type="button"] {
            background-color: darkgray;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-group input[type="button"]:hover {
            background-color: gray;
        }
    </style>
</head>
<body>
<div class="board-form-container">
    <h1>게시글</h1>
    <form action="/board/update" method="post">
        <div class="form-group">
            <label for="no">번호:</label>
            <input type="text" id="no" name="no" value="${board.no}" readonly>
        </div>
        <div class="form-group">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" value="${board.title}" required>
        </div>
        <div class="form-group">
            <label for="content">내용:</label>
            <textarea id="content" name="content" required>${board.content}</textarea>
        </div>
        <div class="form-group">
            <label for="attached-files">첨부파일:</label>
            <ul id="attached-files">
            <c:forEach items="${board.attachedFiles}" var="attachedFile">
                <li><a href="/board/download?filename=${attachedFile.filename}">${attachedFile.originFilename}</a></li>
            </c:forEach>
            </ul>
        </div>
        <div class="form-group">
            <label for="writer">작성자:</label>
            <input type="text" id="writer" value="${board.writer.name}" readonly>
        </div>
        <div class="form-group">
            <label for="createDate">작성일:</label>
            <input type="text" id="createDate" value="${board.createDate}" readonly>
        </div>
        <div class="form-group">
            <label for="viewCount">조회수:</label>
            <input type="text" id="viewCount" value="${board.viewCount}" readonly>
        </div>
        <div class="form-group">
            <input type="submit" value="변경">
            <input type="button" value="삭제" onclick="deleteBoard(${board.no})">
        </div>
    </form>
    <a href="/board/list">목록</a>
</div>

<script>
function deleteBoard(no) {
    location.href = "/board/delete?no=" + no;
}
</script>
</body>
</html>