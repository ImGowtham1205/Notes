<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
</head>

<body data-context-path="${pageContext.request.contextPath}">

<%
	//Disable cache
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP 1.1
	response.setHeader("pragma","no-cache");//HTTP 1.0
	response.setHeader("Expires", "0");//Proxy Server
	
	// Redirect to access if user not logged in
    String mail = (String) request.getAttribute("Mail");
    if (mail == null) {
        response.sendRedirect("/access");
        return;
    }
    
    String name = (String) request.getAttribute("name");
%>

<header class="top-bar" id="mainBar">
    <h1 class="app-title">welcome <%= name %></h1>

    <!-- Hamburger -->
    <button class="menu-toggle" id="menuToggle" aria-label="Open Menu">
        <span></span>
        <span></span>
        <span></span>
    </button>
</header>

<nav class="side-menu" id="sideMenu">
    <div class="menu-header"><%= name %> Account</div>

    <form action="welcome">
        <button class="menu-item">Home</button>
    </form>

	<form action="deleteuser">
        <button class="menu-item">Delete Account</button>
    </form>

    <form action="logout" method="get">
        <button class="menu-item logout">Logout</button>
    </form>
</nav>

<!-- BACKDROP OVERLAY -->
<div class="backdrop" id="backdrop"></div>

<!-- PAGE CONTENT -->
<div class="container">

    <form class="reset-box" id="resetForm" method="post" action="changepassword" novalidate>
	
	<!-- Message -->
        <%
            Object statusObj = request.getAttribute("status");
            if (statusObj != null && "fail".equals(statusObj.toString())) {
        %>
                <div class="error-msg" id="serverErrorMsg">
                    Current Password Not Matched...
                </div>
        <%
            } else if (statusObj != null && "success".equals(statusObj.toString())) {
        %>
                <div class="success-msg" id="serverSuccessMsg">
                    Password updated successfully
                </div>
        <% } %>

        <h2>Change Password</h2>

        <p class="password-info">
            Password must contain minimum 8 characters with 1 UpperCase,
            1 LowerCase, 1 Number and 1 Special Character.
        </p>

        <div class="input-group">
            <label for="currentPass">Current Password</label>
            <input type="password" id="currentPass" name="currentPassword">
            <span class="toggle" onclick="togglePass('currentPass', this)">👁️</span>
        </div>

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

        <p id="error" class="error"></p>

        <button type="submit" class="btn">Update Password</button>

    </form>

</div>

<script src="${pageContext.request.contextPath}/js/reset.js"></script>

</body>
</html>
