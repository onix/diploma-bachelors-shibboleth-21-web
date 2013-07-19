<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../views/elements/common-head.jsp"/>
    <title>Stego ACS Web Console — General Employees List</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <p>
        <a href="${pageContext.request.contextPath}/employees/create-new-employee">
            <button type="submit" class="btn btn-large btn-primary">Add New Employee</button>
        </a>
    </p>

    <table class="table" id="employeesList">
        <thead><tr><th>id</th><th>Name</th><th>Surname</th><th>mobile</th><th>e-mail</th><th></th></tr></thead>
        <tbody>
        </tbody>
    </table>
    <jsp:include page="../views/elements/common-spinner-loader.jsp"/>
</div>

<jsp:include page="../views/elements/common-foot-javascript.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/employees-table.js"></script>

</body>
</html>