<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../views/elements/common-head.jsp"/>
    <title>Stego ACS Web Console — Search by ${searchBy} results</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <c:choose>
        <c:when test="${not empty employeesList}">
            <p>For ${searchBy} '<i>${query}</i>' we've found next employees:</p>
            <table class="table">
                <th>id</th><th>Name</th><th>Surname</th><th>Second Name</th><th>email</th><th>Phone</th><th></th>
                <c:forEach var="employee" items="${employeesList}">
                    <tr>
                        <td>${employee.idEmployee}</td>
                        <td>${employee.name}</td>
                        <td>${employee.surname}</td>
                        <td>${employee.secondName}</td>
                        <td>${employee.email}</td>
                        <td>${employee.phone}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/passes/get/${employee.idEmployee}"><button class="btn">Get Pass Image</button></a>
                            <a href="${pageContext.request.contextPath}/employees/edit/${employee.idEmployee}"><button class="btn btn-info">Edit Employee</button></a>
                            <button class="btn btn-danger">Remove</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>

        <c:otherwise>
            <H1>Nothing found. Try <a href = "${pageContext.request.contextPath}/search">new query</a>.</H1>
        </c:otherwise>
    </c:choose>

</div>

<jsp:include page="../views/elements/common-foot-javascript.jsp"/>

</body>
</html>