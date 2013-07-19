<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../../views/elements/common-head.jsp"/>
    <title>Stego ACS Web Console — Authentication Success</title>
</head>
<body>

<jsp:include page="../elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <h1>Authentication ${authStatus}!</h1>
    <p>${authMessage}</p>
    <div class="span4 well">
        <div class="row">
            <div class="span1">
                <a href="${pageContext.request.contextPath}/employees/get-employee-photo/${idEmployee}" class="thumbnail">
                    <img src="data:image/png;base64, ${employeePhoto}" alt="Employee Photo"/>
                </a>
            </div>
            <div class="span3">
                <p>${employeeName} ${employeeSecondName} ${employeeSurname}</p>
                <p><strong>id:</strong> ${idEmployee}</p>
                <p><strong>address:</strong> ${employeeAddress}</p>
                <p><strong>mobile:</strong> ${employeePhone}</p>
                <p><strong>email:</strong> ${employeeEmail}</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../../views/elements/common-foot-javascript.jsp"/>

</body>
</html>