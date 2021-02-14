<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
        integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

  <style>
    .video-selected:hover {
      /*边框阴影*/
      box-shadow: 0px -1px 0px 0px #888888,-1px 0px 0px 0px #888888,
      1px 0px 0px 0px #888888 ,0px 1px 0px 0px #888888;
      /*    鼠标变小手 */
      cursor: pointer;
      /*    文字变颜色 */
      color: red;
    }

  </style>

  <title>首页</title>
</head>
<body>
<jsp:include page="../jsp/common/header.jsp"></jsp:include>
<br><br>
<!-- 轮播图 -->
<div class="container">

  <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
      <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
      <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
      <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="http://localhost:8080/static/img/456.jpg" class="d-block w-100" alt="...">
      </div>
      <div class="carousel-item">
        <img src="http://localhost:8080/static/img/456.jpg" class="d-block w-100" alt="...">
      </div>
      <div class="carousel-item">
        <img src="http://localhost:8080/static/img/456.jpg" class="d-block w-100" alt="...">
      </div>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>
<br>

<div class="container">

  <a href="#" class="float-right">更多 ></a>
  <h4 class="text-center">常用框架</h4>
  <hr class="btn-dark">

  <div class="row">
    <c:forEach items="${pageType1_topics.list}" var="topic">
      <div class="col-3">
        <a href="/topic/${topic.id}">
          <div class="card video-selected" style="width: 14rem;">
            <img src="http://localhost:8080/static/img/123.jpg" class="card-img-top" alt="...">
            <div class="card-body">
              <h5 class="card-title">${topic.topicName}</h5>
              <p class="card-text">${topic.views}</p>
              <a href="/topic/${topic.id}" class="badge badge-success">免费</a>
            </div>
          </div>
        </a>
    </div>
    </c:forEach>
  </div>
  <br>

  <a href="#" class="float-right">更多 > </a>
  <h4 class="text-center">数据库</h4>
  <hr class="btn-dark">

  <div class="row">
    <c:forEach items="${pageType3_topics.list}" var="topic">
      <div class="col-3">
        <a href="/topic/${topic.id}">
          <div class="card video-selected" style="width: 14rem;">
            <img src="http://localhost:8080/static/img/789.jpg" class="card-img-top" alt="...">
            <div class="card-body">
              <h5 class="card-title">${topic.topicName}</h5>
              <p class="card-text">${topic.views}</p>
              <a href="#" class="badge badge-success">免费</a>
            </div>
          </div>
        </a>
      </div>
    </c:forEach>
  </div>
  <br><br><br>
</div>

<jsp:include page="../jsp/common/footer.jsp"></jsp:include>

</body>
</html>