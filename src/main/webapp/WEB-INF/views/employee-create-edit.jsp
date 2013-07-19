<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="elements/common-head.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/libs/bootstrap-fileupload/bootstrap-fileupload.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/libs/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/validation-errors.css">

    <title>Stego ACS Web Console — ${pageTitle}</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <form name="createNewEmployeeForm" id="employeeDataForm" class="validErrors" enctype="multipart/form-data" method="POST" action="${pageContext.request.contextPath}/employees/${formProcessUrl}">
        <legend>${pageTitle}</legend>
        <div class="row">
            <div class="span5">
                <label>Name</label>
                <div class="input-prepend"><span class="add-on"><i class="icon-user"></i></span>
                    <input type="text" name="employeeName" placeholder="Employee Name" value="${employee.name}">
                </div>

                <label>Surname</label>
                <div class="input-prepend"><span class="add-on"><i class="icon-user"></i></span>
                    <input type="text" name="employeeSurname" placeholder="Employee Surname" value="${employee.surname}">
                </div>
                <label>Second Name</label>
                <div class="input-prepend"><span class="add-on"><i class="icon-user"></i></span>
                    <input type="text" name="employeeSecondName" placeholder="Employee Second Name" value="${employee.secondName}">
                </div>
                <label>Address</label>
                <div class="input-prepend"><span class="add-on"><i class="icon-home"></i></span>
                    <textarea rows="5" name="employeeAddress" placeholder="Employee Address Of Living">${employee.address}</textarea>
                </div>
                <label>Phone</label>
                <div class="input-prepend"><span class="add-on"><i class="icon-th"></i></span>
                    <input type="text" name="employeePhone" placeholder="Employee Phone Number" value="${employee.phone}">
                </div>
                <label>email</label>
                <div class="input-prepend"><span class="add-on"><i class="icon-envelope"></i></span>
                    <input type="text" name="employeeEmail" placeholder="Employee e-mail Address" value="${employee.email}">
                </div>
            </div>

            <label>Photo</label>
            <div class="span4">
                <c:choose>
                    <c:when test="${editMode == true}">
                        <jsp:include page="elements/upload-photo-element-edit.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="elements/upload-photo-element.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="span3">
                <p><i>It's required to upload photo of *.png or *.jpg file formats. It's highly recommended to upload previously prepared photo, with size 354x472 px. Bigger photos will be resized and cropped by this ratio.</i></p>
            </div>

            <div class="span7">
                <c:if test="${editMode == true}">
                    <label>Is pass active</label>
                    <c:if test="${pass.passActive == true}"><c:set var="isPassActive" value="checked"/></c:if>
                    <input type="checkbox" id="isPassActive" name="isPassActive" value="isActive" ${isPassActive}/>

                    <label>Date of total expiration (Standard Time +0000 UTC)</label>
                    <label>(in format yyyy-MM-dd hh:mm:ss, where hh in 24-hour format)</label>
                    <label>Leave empty for unlimited period.</label>
                    <div id="datetimepicker1" class="input-append date">
                        <input name="passExpirationDate" data-format="yyyy-MM-dd hh:mm:ss" type="text" value="${pass.dateOfExpiration}"/>
                        <span class="add-on">
                            <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                        </span>
                    </div>

                    <label>Current Authenticator ID</label>
                    <textarea rows="5" id="authenticatorID" readonly="readonly">${authId}</textarea>
                    <button class="btn" id="generateNewAuthenticatorID">Generate new AuthID</button>
                </c:if>
                <br />
                <button type="submit" class="btn btn-large btn-success">Submit</button>
            </div>
    </form>
</div>

<jsp:include page="elements/common-foot-javascript.jsp"/>
<jsp:include page="elements/validation-javascript.jsp"/>
<script src="${pageContext.request.contextPath}/static/js/create-new-employee-validation-rules.js"></script>
<script src="${pageContext.request.contextPath}/static/libs/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
<c:if test="${editMode == true}">
    <script src="${pageContext.request.contextPath}/static/libs/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#datetimepicker1').datetimepicker({
                language: 'en-GB'
            });
        });
        $().ready(
                $(function() {
                    $('#generateNewAuthenticatorID').click(function(e) {
                        e.preventDefault();
                        if (confirm('Are you sure you want to regenerate Authenticator? Your previous one will not be available!')) {
                            $.post(
                                    '/passes/regenerate-authenticator/${employee.idEmployee}',
                                    function(data) {
                                        $('textarea#authenticatorID').val(data);
                                    }
                            )
                        }
                    });
                })
        );
    </script>
</c:if>

</body>
</html>