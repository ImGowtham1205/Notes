<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Account</title>
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
                <span class="brand-title">Create Account</span>
                <img src="${pageContext.request.contextPath}/images/favicon.jpeg" alt="App logo" class="brand-logo" />
                <p class="brand-text">
                    Join and start managing your notes more efficiently.
                </p>
            </div>
        </div>

        <!-- RIGHT PANEL -->
        <div class="login-right">
            <div class="login-header">
                <h3>Create your account</h3>
            </div>
            <!-- Message -->
             <% 
                    Object statusObj = request.getAttribute("status");
                    if (statusObj != null && "success".equals(statusObj.toString())) {
                %>
                     <div class="success-msg" id="serverSuccessMsg">Account Registered Successfully</div>
                <% } else if (statusObj != null && "fail".equals(statusObj.toString())) {%>
                		<div class="error-msg" id="serverErrorMsg">Registration Failed. Entered Email Is Already Registered. Register With Another Email.</div>
            	<%} %>
            <form id="registerForm" action="register" method="post" novalidate>
                <!-- USERNAME -->
                <div class="form-group">
                    <label for="username">UserName</label>
                    <input type="text" id="username" name="username" placeholder="Enter Your Name" required/>
                    <div class="field-error" id="usernameError"></div>
                </div>

                <!-- EMAIL -->
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="you@example.com" required />
                    <div class="field-error" id="emailError"></div>
                </div>

                <!-- PASSWORD -->
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Create a strong password" required/>
                    <div class="field-error" id="passwordError"></div>
                    <div class="password-hint">
                        Password must be at least 8 characters with 1 UpperCase, 1 LowerCase, 1 Number, and 1 Special Character.
                    </div>
                </div>

                <button type="submit" class="btn-primary">Register</button>

                <div class="divider">
                    <span></span>
                    <span class="divider-label">or</span>
                    <span></span>
                </div>

                <p class="signup-text"> Already have an account? <a href="/">Login</a> </p>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/register.js"></script>
</body>
</html>
