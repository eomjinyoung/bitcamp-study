<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새 게시글</title>
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
            width: 500px;
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
    </style>
</head>
<body>
<div class="board-form-container">
    <h1>새 게시글</h1>
    <form action="/board/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="content">내용:</label>
            <textarea id="content" name="content" required></textarea>
        </div>
        <div class="form-group">
            <label for="files">첨부파일:</label>
            <input type="file" id="files" name="files" multiple>
        </div>
        <div class="form-group">
            <input type="submit" value="등록">
        </div>
    </form>
</div>
</body>
</html>