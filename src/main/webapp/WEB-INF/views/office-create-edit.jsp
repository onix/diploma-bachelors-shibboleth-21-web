<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="elements/common-head.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/validation-errors.css">

    <title>Stego ACS Web Console — ${headerTitle}</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <form name="officeCredentials" id="officeCredentials" class="validErrors" method="POST" action="${pageContext.request.contextPath}/offices/${formSendUrlPart}">
        <fieldset>
            <legend>${pageLegend}</legend>
            <label>Office Name</label>
            <label>
                <div class="input-prepend"><span class="add-on"><i class="icon-tag"></i></span>
                    <input type="text" name="officeName" placeholder="Short Office Name (Tag)" value="${officeName}">
                </div>
            </label>

            <label>Office Address</label>
            <label>
                <div class="input-prepend"><span class="add-on"><i class="icon-home"></i></span>
                    <textarea rows="5" name="officeAddress" placeholder="Office Location">${officeAddress}</textarea>
                </div>
            </label>

            <button type="submit" class="btn btn-large btn-success">Submit</button>
        </fieldset>
    </form>
</div>

<jsp:include page="elements/common-foot-javascript.jsp"/>
<jsp:include page="elements/validation-javascript.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/office-create-edit-validation-rules.js"></script>


</body>
</html>