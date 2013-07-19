<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="photoUploader" name="photoUploader" class="fileupload fileupload-new" data-provides="fileupload">
    <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
        <img src="${pageContext.request.contextPath}/static/img/no-image.gif" />
    </div>
    <div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
    <div>
        <span class="btn btn-file">
            <span class="fileupload-new">Select image</span>
            <span class="fileupload-exists">Change</span>
            <input type="file" name="photo"/>
        </span>
        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
    </div>
</div>
<br />