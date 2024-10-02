<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>등록</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h1 style="text-align:center">등록</h1>
    <div class="container">
        <div class="panel-body">
            <form action="/todo/register" method="post">
                <div class="form-group">
                    <label for="todoTitle">제목</label>
                    <input class="form-control" id="todoTitle" name="title" value="${todo.title}">
                </div>
                <div class="form-group">
                    <label for="todoWriter">글쓴이</label>
                    <input class="form-control" id="todoWriter" name="writer" required>
                </div>
                <div class="form-group">
                    <label for="todoDueDate">기간</label>
                    <input class="form-control" type="date" id="todoDueDate" name="dueDate" required>
                </div>
                <div class="form-group">
                    <label for="todoFinished">완료 여부</label>
                    <select class="form-control" id="todoFinished" name="finished" required>
                        <option value="0">미완료</option>
                        <option value="1">완료</option>
                    </select>
                </div>
                <div class="mt-3">
                    <button type="submit" class="btn btn-primary">등록</button>
                    <a href="/todo/list" class="btn btn-secondary">취소</a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
