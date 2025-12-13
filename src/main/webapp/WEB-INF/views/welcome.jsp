<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.example.notes.model.UserTask"%>
<%@ page import="java.time.LocalDateTime" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Notes – Welcome</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/welcome.css" />
</head>
<body data-context-path="${pageContext.request.contextPath}">
<%  
// Disable cache
response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP 1.1
response.setHeader("Pragma","no-cache");//HTTP 1.0
response.setHeader("Expires", "0");//Proxy Server   

//Redirect to access if user not logged in
String mail = (String) request.getAttribute("Mail");
if (mail == null) {
    response.sendRedirect("/access");
    return;
}

@SuppressWarnings("unchecked")
List<UserTask> usertask =(List<UserTask>)request.getAttribute("task");
String name = (String) request.getAttribute("name");
%>

<input type="hidden" name="Mail" value="<%=mail%>">

<!-- NORMAL TOP BAR (with hamburger) -->
<header class="top-bar" id="mainBar">
    <h1 class="app-title">welcome <%= name %></h1>
    <!-- Hamburger -->
    <button class="menu-toggle" id="menuToggle" aria-label="Open Menu">
        <span></span> <span></span> <span></span>
    </button>
</header>

<!-- SELECTION TOP BAR (shows when item selected) -->
<header class="top-bar select-mode" id="selectBar">
    <button class="icon-btn" id="cancelSelect">✕</button>
    <h2 id="selectedCount">0 item selected</h2>
</header>

<nav class="side-menu" id="sideMenu">
    <div class="menu-header"><%= name %> Account</div>      
    <button class="menu-item" id="changePasswordBtn">Change Password</button>   
    <button class="menu-item" id="deleteAccountBtn">Delete Account</button>    
    <button class="menu-item logout" id="logoutBtn">Logout</button>      
</nav>

<!-- DARK OVERLAY FOR MENU -->
<div class="backdrop" id="backdrop"></div>

<form id="deleteForm" method="post" action="${pageContext.request.contextPath}/deletetask" style="display:none;">
    <!-- JS will append <input name="taskids" value="..."> elements here -->
</form>

<!-- NOTES LIST -->
<main class="content">
    <section class="notes-list">
        <% 
            if(usertask == null || usertask.isEmpty()){
        %>
            <article class="note-card placeholder">
                <div class="note-left">
                    <h3>Let's create your first task</h3>
                </div>
                <div class="circle"></div>
            </article>
        <% } else {
            for(UserTask task : usertask){
                int id = task.getTask_id();
                String title = task.getTitle();
                String description = task.getDescription();
                LocalDateTime time = task.getCreated_at();
        %>
            <article class="note-card" data-id="<%= id %>">
                <div class="note-left">
                    <h3><%= title %></h3>
                    <p><%= description %></p>
                    <span class="time"><%= time %></span>
                </div>
                <div class="circle"></div>
            </article>
        <%  }
           } %>
    </section>
</main>

<button class="fab" id="addNoteBtn">+</button>

<!-- BOTTOM BAR WITH ONLY DELETE BUTTON -->
<div class="bottom-delete" id="bottomDelete">
    <button class="delete-btn" id="deleteBtn">Delete</button>
</div>

<script src="${pageContext.request.contextPath}/js/welcome.js"></script>
</body>
</html>
