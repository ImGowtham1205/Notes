<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Notes Portal</title>
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
        <div class="login-left">
            <div class="login-brand">
                <span class="brand-title">Notes Portal</span>
                <img src="${pageContext.request.contextPath}/images/favicon.jpeg"
                     alt="Notes Portal logo"
                     class="brand-logo" />
                <p class="brand-text">
                    Capture, organize and revisit your ideas anytime, anywhere.
                </p>
            </div>
        </div>

        <div class="login-right">
            <div class="login-header">
                <% 
                    Object statusObj = request.getAttribute("status");
                    if (statusObj != null && "fail".equals(statusObj.toString())) {
                %>
                    <div class="error-msg" id="serverErrorMsg">Incorrect email or password</div>
                <% } %>

                <h3>Sign in to continue to your account</h3>
            </div>

            <form id="loginForm" action="validate" method="post" novalidate>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input  type="email" id="email" name="email" placeholder="you@example.com" required/>
                    <div class="field-error" id="emailError"></div>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required/>
                    <div class="field-error" id="passwordError"></div>
                </div>

                <div class="form-row">
                    <div class="forgot-link">
                        <a href="forgot">Forgot password?</a>
                    </div>
                </div>

                <button type="submit" class="btn-primary">Login</button>

                <div class="divider">
                    <span></span>
                    <span class="divider-label">or</span>
                    <span></span>
                </div>

                <p class="signup-text">
                    Don't have an account?
                    <a href="createuser">Sign up</a>
                </p>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>
