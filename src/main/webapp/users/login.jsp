<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/header.jsp" %>

<div class="container">
    <div class="row justify-content-center">

        <div class="col-xl-6 col-lg-6 col-md-8">
            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-5">
                    <div class="text-center">
                        <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                    </div>
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger text-center">${message}</div>
                    </c:if>

                    <form action="<c:url value='/admin/login'/>" method="post">
                        <div class="form-group">
                            <label for="userEmail">Email</label>
                            <input name="email" type="email" class="form-control" id="userEmail" placeholder="Enter email">
                        </div>

                        <div class="form-group">
                            <label for="userPassword">Password</label>
                            <input name="password" type="password" class="form-control" id="userPassword" placeholder="Enter password">
                        </div>

                        <button type="submit" class="btn btn-primary btn-block">Login</button>
                    </form>

                    <hr>
                    <div class="text-center">
                        <a class="small" href="<c:url value='/admin/register'/> ">Create an Account!</a>
                    </div>

                    <div class="text-center mt-2">
                        <a class="small" href="<c:url value='/admin/main'/>">Return to Main</a>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>

<%@ include file="/footer.jsp" %>
