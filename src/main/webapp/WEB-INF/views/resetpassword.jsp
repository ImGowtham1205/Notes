<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
</head>
<body>
	
	<%
		//Disable cache
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP 1.1
		response.setHeader("pragma","no-cache");//HTTP 1.0
		response.setHeader("Expires", "0");//Proxy Server	
	%>
	
<div class="container">
    <form class="reset-box" id="resetForm" method="post" action="forgotpassword" novalidate>

        <% 
            Object statusObj = request.getAttribute("status");
            if (statusObj != null && "fail".equals(statusObj.toString())) {
        %>
                <div class="error-msg" id="serverErrorMsg">
                    Invalid or expired reset link.
                </div>
        <% 
            } else if (statusObj != null && "success".equals(statusObj.toString())) {
        %>
                <div class="success-msg" id="serverSuccessMsg">
                    Password updated successfully. You can now login.
                </div>
        <% } %>

        <h2>Reset Password</h2>

        <p class="password-info">
            Password must contain minimum 8 characters with 1 UpperCase, 1 LowerCase,
            1 Number and 1 Special Character.
        </p>

        <div class="input-group">
            <label for="newPass">New Password</label>
            <input type="password" id="newPass" name="newPassword">
            <span class="toggle" onclick="togglePass('newPass', this)">👁️</span>
        </div>

        <div class="input-group">
            <label for="confirmPass">Confirm Password</label>
            <input type="password" id="confirmPass" name="confirmPassword">
            <span class="toggle" onclick="togglePass('confirmPass', this)">👁️</span>
        </div>

        <!-- Client-side validation error -->
        <p id="error" class="error"></p>

        <!-- hidden reset token -->
        <input type="hidden" name="token" value="${token != null ? token : param.token}" />

        <button type="submit" class="btn">Update Password</button>
    </form>
    
    <p class="signup-text">
        Back to login page <a href="/">Login</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/js/reset.js"></script>

</body>
</html>
