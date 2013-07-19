<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../elements/common-head.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/libs/bootstrap-select/bootstrap-select.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/libs/bootstrap-fileupload/bootstrap-fileupload.min.css">
    <title>Stego ACS Web Console — Process Pass Image</title>
</head>
<body>

<jsp:include page="../elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <form enctype="multipart/form-data" method="POST" action="${pageContext.request.contextPath}/passes/check">

        <label>Choose an office from dropdown you want to authenticate</label>
        <form:select path="office" items="${officesList}" class="selectpicker" id="office" name="office"/>

        <label>Upload your pass image</label>
        <jsp:include page="../elements/upload-photo-element.jsp"/>
        <button type="submit" class="btn btn-large btn-primary">Submit</button>
    </form>
</div>

<jsp:include page="../elements/common-foot-javascript.jsp"/>
<script src="${pageContext.request.contextPath}/static/libs/bootstrap-select/bootstrap-select.min.js"></script>
<script src="${pageContext.request.contextPath}/static/libs/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
<script type="text/javascript">$('.selectpicker').selectpicker();</script>


</body>
</html>