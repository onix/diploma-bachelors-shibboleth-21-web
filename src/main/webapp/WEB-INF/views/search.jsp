<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../views/elements/common-head.jsp"/>
    <title>Stego ACS Web Console — Search Page</title>
</head>
<body>

<jsp:include page="elements/common-navigation-bar.jsp"/>

<div class="container" id="container">
    <form name="searchForm" id="searchForm" class="validErrors" method="GET"
          action="${pageContext.request.contextPath}/search/find">

        <fieldset>
            <legend>Search an Employee</legend>
            <label>Select criteria and enter query</label>
            <label>
                <input type="radio" name="criteria" value="surname" checked>Surname<br>
                <input type="radio" name="criteria" value="phone">Phone<br>
                <input type="radio" name="criteria" value="email">Email<br>
            </label>
            <label>
                <div class="input-prepend"><span class="add-on"><i class="icon-search"></i></span>
                    <input type="text" name="query" placeholder="Search query">
                </div>
            </label>

            <button type="submit" class="btn btn-large btn-info">Search</button>
        </fieldset>
    </form>
</div>

<jsp:include page="../views/elements/common-foot-javascript.jsp"/>

</body>
</html>