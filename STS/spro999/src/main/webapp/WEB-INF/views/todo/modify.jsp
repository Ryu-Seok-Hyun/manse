<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container mt-4">
    <div class="row">
        <div class="col-lg-8 offset-lg-2">
            <div class="card">
                <div class="card-header">
                    <h3>게시글 수정</h3>
                </div>
                <div class="card-body">
                    <form role="form" action="/todo/modify" method="post" id="modForm">
                        <input type="hidden" name="tno" value="${todo.tno }">
                        
                        <div class="form-group">
                            <label for="title">제목</label>
                            <input type="text" class="form-control" id="title" name="title" value="${todo.title }">
                        </div>
                        <div class="form-group">
                            <label for="writer">글쓴이</label>
                            <input type="text" class="form-control" id="writer" name="writer" value="${todo.writer }" >
                        </div>
                        <div class="form-group">
                            <label for="dueDate">기한</label>
                            <input type="date" class="form-control" id="dueDate" name="dueDate" value="${todo.dueDate}">
                        </div>
                        <div class="form-group">
                            <label for="finished">완료여부</label>
                            <select class="form-control" id="finished" name="finished">
                                <option value="0" ${todo.finished == 0 ? 'selected' : ''}>미완료</option>
                                <option value="1" ${todo.finished == 1 ? 'selected' : ''}>완료</option>
                            </select>
                        </div>

                        <button type="submit" data-oper="modify" class="btn btn-primary">수정</button>
                        <button type="submit" data-oper="remove" class="btn btn-danger">삭제</button>
                        <button type="submit" data-oper="list" class="btn btn-info">목록</button>
                    </form>
                </div>
                <!-- end card-body -->
            </div>
            <!-- end card -->
        </div>
        <!-- end col -->
    </div>
    <!-- end row -->
</div>
<!-- end container -->

<script type="text/javascript">
$(document).ready(function() {
    const formObj = $("#modForm");

    $('button').on("click", function(e) {
        e.preventDefault();
        const operation = $(this).data("oper"); 
        console.log("operation====>", operation);

      
        if (operation === 'remove') {
            formObj.attr("action", "/todo/remove");
            formObj.attr("method", "POST");
        } else if (operation === 'modify') {
            formObj.attr("action", "/todo/modify");
            formObj.attr("method", "POST");
        } else if (operation === 'list') {
            formObj.attr("action", "/todo/list");
            formObj.attr("method", "GET");
        }

        formObj.submit(); 
    });
});
</script>

</body>
</html>
