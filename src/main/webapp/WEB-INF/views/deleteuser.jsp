<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Delete Account - Notes</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/deleteuser.css" />
</head>

	<%
		String mail = (String)request.getAttribute("Mail");
		if(mail == null){
			response.sendRedirect("/access");
		    return;
		}
		
		String name = (String)request.getAttribute("name");
	%>

<body data-context-path="${pageContext.request.contextPath}">

  <header class="top-bar" id="mainBar">
      <h1 class="app-title">welcome <%= name %></h1>

      <!-- Hamburger -->
      <button class="menu-toggle" id="menuToggle" aria-label="Open Menu">
          <span></span> <span></span> <span></span>
      </button>
  </header>

  <!-- SIDE MENU (same IDs and buttons as welcome.jsp) -->
  <nav class="side-menu" id="sideMenu" aria-hidden="true">
      <div class="menu-header"><%= name %> Account</div>
      <button class="menu-item" id="homeBtn">Home</button>
      <button class="menu-item" id="changePasswordBtn">Change Password</button>
      <button class="menu-item logout" id="logoutBtn">Logout</button>
  </nav>

  <!-- DARK OVERLAY FOR MENU -->
  <div class="backdrop" id="backdrop"></div>

  <main class="page" role="main">
    <section class="card" aria-labelledby="delete-heading">
      <div class="title">
        <h1 id="delete-heading">Delete Your Notes Account</h1>
      </div>

      <div class="warning" role="alert" aria-live="polite">
        <strong>Warning:</strong> When you delete your Notes account, all your tasks and notes will be deleted permanently.
      </div>

      <p class="lead">Are you sure you want to delete your account? This action cannot be undone.</p>

      <div class="controls" id="initial-controls">
        <!-- Confirm does not submit — it reveals the password form -->
        <button id="confirm-btn" type="button" class="btn-danger" aria-controls="password-area" aria-expanded="false">
          Confirm
        </button>

        <a href="${pageContext.request.contextPath}/deleteuser" class="btn-ghost" role="button" title="Go back to dashboard">Cancel</a>
      </div>

      <!-- Hidden password area (revealed after Confirm) -->
      <div id="password-area" class="password-area" aria-hidden="true">
        <form class="delete-form" id="delete-form" method="post" action="${pageContext.request.contextPath}/delete-user" novalidate>
         
         	<input type="hidden" name=mail value="<%= mail%>">
         	
          <div>
            <label for="password">For security, enter your password to delete your account</label>
            <input id="password" name="password" type="password"  required
             placeholder="Enter your password" aria-describedby="pw-note">
            <div id="pw-note" class="note">We require your password to confirm this action.</div>
          </div>
		
			<% 
                    Object statusObj = request.getAttribute("status");
                    if (statusObj != null && "fail".equals(statusObj.toString())) {
                %>
                    <div class="error-msg" id="serverErrorMsg">Incorrect password</div>
                <% } %>
                		
          <div class="form-actions">
            <button id="submit-btn" class="btn-danger" type="submit">Delete account</button>
            <button id="cancel-btn" class="btn-ghost" type="button">Back</button>
          </div>
        </form>
      </div>
    </section>
  </main>
  <script src="${pageContext.request.contextPath}/js/deleteuser.js"></script>
</body>
</html>
