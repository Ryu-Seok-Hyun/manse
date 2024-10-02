<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
<style>
    .panel-red {
        border-color: #d9534f;
    }

    .panel {
        margin-bottom: 20px;
        background-color: #fff;
        border: 1px solid transparent;
        border-radius: 4px;
        box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    }

    .panel-heading {
        color: #fff;
        padding: 10px;
        font-size: xx-large; /* 중복된 font-size 속성 제거 */
        border-bottom: 1px solid;
        border-top-left-radius: 4px;
        border-top-right-radius: 4px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        text-align: center; /* 가운데 정렬 */
        background-color: black;
    }

    .panel-body {
        padding: 15px;
    }

    .move {
        color: #d9534f;
        text-decoration: none;
    }

    .move:hover {
        text-decoration: underline;
    }

    .btn-no-border {
        border: none;
        outline: none;
        background-color: #dc3545; 
        color: white; 
    }

   
    .card-header {
        transition: color .1s ease-in-out;
    }
</style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active"><a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a></li>
                <li class="nav-item"><a class="nav-link" href="#">Features</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
                <li class="nav-item"><a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a></li>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <!-- Red Panel -->
        <div class="panel panel-red">
            <div class="panel-heading">
                ★꿈나무의 일정 목록★
                <a href="/todo/register?tno=${todo.tno}" class="move btn btn-no-border">등록하기</a>
            </div>
            <div class="panel-body">
                <div class="row">
                    <c:forEach items="${list}" var="todo">
                        <div class="col-md-4 mb-4">
                            <div class="card border-danger">
                                <div class="card-header bg-danger text-white">
                                    <h5 class="card-title">${todo.title}</h5>
                                </div>
                                <div class="card-body">
                                    <h6 class="card-subtitle mb-2 text-muted">Tno: ${todo.tno}</h6>
                                    <p class="card-text">Writer: ${todo.writer}</p>
                                    <p class="card-text">DueDate: ${todo.dueDate}</p>
                                    <p class="card-text">Finished: ${todo.finished == 1 ? '완료' : '미완료'}</p>
                                    <a class="move" href="/todo/get?tno=${todo.tno}">상세보기</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

<!-- 반짝반 -->
<!--  -->
    <script>
       
        const heading = document.querySelector(".navbar-brand");
        const head = document.querySelector(".panel-heading");
        const cardHeaders = document.querySelectorAll(".card-header"); 

      
        function randomColor() {
            const color = "#" + Math.floor(Math.random() * 16777215).toString(16);

            if (heading) {
                heading.style.color = color;
            }
            if (head) {
                head.style.color = color;
            }
            cardHeaders.forEach(header => {
                header.style.color = color; 
            });
        }

       
        let intervalId = setInterval(randomColor, 100);

       
        if (heading) {
            heading.style.transition = "color .1s ease-in-out";
        }
        if (head) {
            head.style.transition = "color .1s ease-in-out";
        }
        cardHeaders.forEach(header => {
            header.style.transition = "color .1s ease-in-out";
        });
    </script>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
</body>
</html>
