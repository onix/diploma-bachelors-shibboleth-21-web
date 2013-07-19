<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../views/elements/common-head.jsp"/>
    <title>Stego ACS Web Console — Authentication Log</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <table class="table" id="logEntriesList">
        <thead><tr><th>id</th><th>Office id</th><th>Result</th><th>Date (Standard Time +0000 UTC)</th><th>Employee id</th></tr></thead>
        <tbody>
        </tbody>
    </table>
    <jsp:include page="../views/elements/common-spinner-loader.jsp"/>
</div>

<jsp:include page="../views/elements/common-foot-javascript.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/auth-log-table.js"></script>

</body>
</html>