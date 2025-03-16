<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/header.jsp" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-xl-10 col-lg-12 col-md-9">
            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Welcome to Admin Panel</h1>
                                </div>
                                <div class="text-center">
                                    <a href="<c:url value='/admin/login' />" class="btn btn-primary btn-user btn-block">
                                        Login
                                    </a>
                                    <a href="<c:url value='/admin/register' />" class="btn btn-secondary btn-user btn-block">
                                        Sign in
                                    </a>
                                </div>
                                <hr>
                                <div class="text-center">
                                    <p class="small text-gray-600">Users CRUD Admin System</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/footer.jsp" %>
