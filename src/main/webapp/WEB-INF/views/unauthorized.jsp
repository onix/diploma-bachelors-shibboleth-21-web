<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="elements/common-head.jsp"/>
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-signin {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }

    </style>
    <title>Stego ACS Web Console  — Unauthorized</title>
</head>
<body>
<div class="container">
    <div class="form-signin">
        <h2 class="form-signin-heading">Unauthorized, please sign in</h2>
        <input type="text" id="j_username" class="input-block-level" placeholder="Login">
        <input type="password" id="j_password" class="input-block-level" placeholder="Password">
        <button name="signinButton" id="signinButton" class="btn btn-large btn-primary" type="submit">Sign in</button>
        <a href="${pageContext.request.contextPath}/passes/check"><button name="" class="btn btn-large btn-primary">Open Demo</button></a>
    </div>
</div>

<jsp:include page="elements/common-foot-javascript.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/unauthorized.js"></script>
</body>
</html>