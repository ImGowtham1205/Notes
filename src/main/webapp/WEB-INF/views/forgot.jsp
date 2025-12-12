<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>

	<%
		//Disable cache
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP 1.1
		response.setHeader("pragma","no-cache");//HTTP 1.0
		response.setHeader("Expires", "0");//Proxy Server	
	%>

<div class="login-wrapper">
    <div class="login-card">

        <!-- LEFT PANEL -->
        <div class="login-left">
            <div class="login-brand">
                <span class="brand-title">Notes Portal</span>
                <img src="${pageContext.request.contextPath}/images/favicon.jpeg" alt="App logo" class="brand-logo" />
                <p class="brand-text">
                    Enter Your Registered Mail Id For To Change Your Password
                </p>
            </div>
        </div>

        <!-- RIGHT PANEL -->
        <div class="login-right">
            <div class="login-header">
                <h3>Forgot Password</h3>
            </div>

            <% 
                Object statusObj = request.getAttribute("status");
                if (statusObj != null && "success".equals(statusObj.toString())) {
            %>
                 <div class="success-msg" id="serverSuccessMsg">Mail Sent Successfully</div>
            <% } else if(statusObj != null && "fail".equals(statusObj.toString())) { %>
                 <div class="error-msg" id="servererrorMsg">Please Enter Your Registered Email</div>
            <% } %>

            <form id="registerForm" action="reset" method="post" novalidate>
                <!-- EMAIL -->
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="you@example.com" required />
                    <div class="field-error" id="emailError"></div>
                </div>
                <button type="submit" class="btn-primary">Send Mail</button>

                <div class="divider">
                    <span></span>
                    <span class="divider-label">or</span>
                    <span></span>
                </div>

                <p class="signup-text">
                    Back To Login page
                    <a href="/">Login</a>
                </p>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/forgot.js"></script>
</body>
</html>
