<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 상세 조회</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body>
<h1 style="text-align:center">상세조회</h1>
    <div class="container mt-4">
        <!-- 게시글 상세 조회 폼 -->
        <form id="operForm" action="/todo/modify" method="get">
            <div class="form-group">
                <label>제목</label>
                <input class="form-control" name="title" value="${todo.title }" readonly>
            </div>
            <div class="form-group">
                <label>글쓴이</label>
                <input class="form-control" name="writer" value="${todo.writer }" readonly>
            </div>
            <div class="form-group">
                <label>기한</label>
                <input class="form-control" name="dueDate" value="${todo.dueDate }" readonly>
            </div>
            <div class="form-group">
                <label>완료여부</label>
                <input class="form-control" name="finished" value="${todo.finished }" readonly>
            </div>

            <input type="hidden" id="tno" name="tno" value="${todo.tno }">
            <button type="button" data-oper="modify" class="btn btn-default">수정</button>
            <button type="button" data-oper="list" class="btn btn-info">목록</button>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            const operForm = $("#operForm");

            // 수정 버튼 클릭 이벤트
            $("button[data-oper='modify']").on("click", function() {
                operForm.attr("action", "/todo/modify").submit();
            });

            // 목록 버튼 클릭 이벤트
            $("button[data-oper='list']").on("click", function() {
                window.location.href = "/todo/list";
            });
        });
    </script>
</body>
</html>
