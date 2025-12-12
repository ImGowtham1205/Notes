<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.example.notes.model.UserTask" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/updatetask.css" />
</head>
<%
	String mail = (String) request.getAttribute("Mail");
	if (mail == null) {
		response.sendRedirect("/access");
		return;	
	}
	
	UserTask task = (UserTask) request.getAttribute("task");
	
	 int taskId = (task != null) ? task.getTask_id() : Integer.parseInt(request.getParameter("taskId"));
	 String title = (task != null) ? task.getTitle() : request.getParameter("title");
	 String description = (task != null) ? task.getDescription() : request.getParameter("description");
%>
<body data-context-path="${pageContext.request.contextPath}">
    <header class="edit-top-bar">
        <!-- Back button -->
        <button type="button" class="icon-btn back-btn" id="backBtn" aria-label="Back">
            ←
        </button>

        <!-- Page title (you can change this text if you want) -->
        <h1 class="edit-title">Edit Task</h1>

        <!-- Tick / Save button -->
        <button type="button" class="icon-btn tick-btn" id="saveTick" aria-label="Save">
            ✔
        </button>
    </header>

    <!-- Edit form -->
    <main class="edit-main">
        <form id="editForm" action="updatetask" method="post">
            <!-- Hidden task id -->
            <input type="hidden" name="taskId" value="<%= taskId%>">

            <!-- Title -->
            <textarea name="title" class="edit-title-input" rows="1" maxlength="200" 
            placeholder="Title"><%= (title != null ? title : "") %></textarea>

            <!-- Description -->
            <textarea name="description" class="edit-body-input" 
            placeholder="Write your note here..."><%= (description != null ? description : "") %></textarea>
        </form>
    </main>

    <script src="${pageContext.request.contextPath}/js/updatetask.js"></script>
</body>
</html>
