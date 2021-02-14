
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar shadow navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="#">周大嘟的视频网站</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item  ${clickNav==1? "active": ""}">
                    <a class="nav-link" href="/">首页 <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item  ${clickNav==2? "active": ""} ">
                    <a class="nav-link" href="/topicList">课程</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">会员</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">直播</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">工具</a>
                </li>
            </ul>
            <c:choose>
                <c:when test="${! empty SESSION_LOGINUSER}">
                    ${SESSION_LOGINUSER.email}&nbsp;<a href="/logout">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="#" data-toggle="modal" data-target="#loginModal">登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;
                    <a href="#" data-toggle="modal" data-target="#registerModal" class="mr-4">注册</a>
                </c:otherwise>
            </c:choose>
            <form action="/search"  class="form-inline my-2 my-lg-0">
                <input  class="form-control mr-sm-2" name="keyword" type="search" placeholder="搜索视频" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button>
            </form>
        </div>
    </div>
</nav>