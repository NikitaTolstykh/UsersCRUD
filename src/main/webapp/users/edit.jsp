<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/header.jsp" %>

<div class="container-fluid">


    <div class="d-sm-flex align-items-center justify-content-between mb-4">

        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>

        <a href="<c:url value="/user/list"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i> Users list</a>
    </div>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Edit user</h6>
        </div>
        <div class="card-body">
            <c:if test="${not empty message}">
                <div class="alert alert-danger text-center">${message}</div>
            </c:if>
            <form action="<c:url value='/user/edit'/>" method="post">
                <div class="form-group">
                    <label for="userName">Name</label>
                    <input name="username" type="text" class="form-control" id="userName" value="${userInfo.username}">
                    <input type="hidden" name="id" value="${userInfo.id}">
                </div>
                <div class="form-group">
                    <label for="userEmail">Email</label>
                    <input name="email" type="email" class="form-control" id="userEmail" value="${userInfo.email}">
                </div>
                <div class="form-group">
                    <label for="userPassword">Password</label>
                    <input name="password" type="password" class="form-control" id="userPassword" placeholder="User password">
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </form>
        </div>
    </div>
</div>


<%@include file="/footer.jsp" %>
