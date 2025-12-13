<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Access Not Allowed</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/access.css">
</head>
<%
	//Disable cache
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP 1.1
	response.setHeader("pragma","no-cache");//HTTP 1.0
	response.setHeader("Expires", "0");//Proxy Server
%>
<body>
<div class="container">
    <div class="card">

        <div class="icon-wrapper">
            <div class="icon">!</div>
        </div>

        <div class="status-text">Access Not Allowed</div>
        <h1>You don’t have permission</h1>

        <div class="code">Error Code: 403 – Forbidden</div>

        <p>
            You’re trying to access a page without enter your login credentials.
        </p>

        <div class="hint">
            Tip: Enter your login proper credentials in login page after that you can able to access this page.
        </div>

        <div class="buttons">
            <button class="btn btn-primary" onclick="history.back()">Go Back</button>
            <button class="btn btn-secondary" onclick="location.href='/'">Go to Home</button>
        </div>

        <div class="footer-text">
            Access control by <span>SecurePortal</span>
        </div>

    </div>
</div>

</body>
</html>
    