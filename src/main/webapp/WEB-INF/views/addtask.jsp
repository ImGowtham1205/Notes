<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Add Task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addtask.css" />
</head>
<%! String mail; %>
<body>

	<%	
		//Disable cache
  		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");//HTTP 1.1
		response.setHeader("pragma","no-cache");//HTTP 1.0
		response.setHeader("Expires", "0");//Proxy Server
		
		// Redirect to access if user not logged in
		mail = (String) request.getAttribute("Mail");
		if(mail==null){
			response.sendRedirect("/access");
			return;
		}	
	%>
	 <form action="newtask" method="post">  
    <header class="top-bar">
    	<button type="button" class="icon-btn" id="backBtn" aria-label="Back">←</button>
        <div class="top-title">Add task</div>
        <button type="submit" class="icon-btn right" id="saveBtn" aria-label="Save">✔</button>
    </header>

    <main class="page">
        <section class="note-box">
            <input type="text" id="titleInput" name="title" class="title-input"placeholder="Title"/>

            <div class="meta" id="metaInfo">
                <!-- Filled by JS: date | 0 characters -->
            </div>
            <textarea id="noteInput" name="description" class="note-input" rows="10"></textarea>
        </section>
    </main>
    <input type="hidden"  name="mail" value=<%= mail %>>
</form>    	
    <script src="${pageContext.request.contextPath}/js/addtask.js"></script>
</body>
</html>
    