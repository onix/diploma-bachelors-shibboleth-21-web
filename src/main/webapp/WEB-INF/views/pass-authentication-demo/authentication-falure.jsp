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
    <h1>Authentication Falure!</h1>
    <p>We can't extract any information from this pass. Our admins know about that.</p>
</div>

<jsp:include page="../../views/elements/common-foot-javascript.jsp"/>

</body>
</html>